package com.solution.sparkkafkaanalyzer.consumer;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

import com.solution.sparkkafkaanalyzer.config.CassandraPropertiesConfig;
import com.solution.sparkkafkaanalyzer.config.KafkaPropertiesConfig;
import com.solution.sparkkafkaanalyzer.deserializer.CustomMeetupRSVPDeserializer;
import com.solution.sparkkafkaanalyzer.model.MeetupRSVP;
import com.solution.sparkkafkaanalyzer.model.Venue;
import com.solution.sparkkafkaanalyzer.model.VenueFrequency;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KafkaSparkMeetUpConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSparkMeetUpConsumer.class);

    private final CassandraPropertiesConfig cassandraPropertiesConfig;

    private final KafkaPropertiesConfig kafkaPropertiesConfig;

    private final JavaStreamingContext context;

    private final MeetupOffsetCommitCallback commitCallback;

    @Autowired
    public KafkaSparkMeetUpConsumer(CassandraPropertiesConfig cassandraPropertiesConfig, KafkaPropertiesConfig kafkaPropertiesConfig, JavaStreamingContext context, MeetupOffsetCommitCallback commitCallback) {
        this.cassandraPropertiesConfig = cassandraPropertiesConfig;
        this.kafkaPropertiesConfig = kafkaPropertiesConfig;
        this.context = context;
        this.commitCallback = commitCallback;
    }

    public void getMeetUpData() {
        final JavaInputDStream<ConsumerRecord<String, MeetupRSVP>> meetupStream = createStream();
        final JavaDStream<MeetupRSVP> meetupStreamValues = meetupStream.map(ConsumerRecord::value);

        updateVenueFrequency(meetupStreamValues);
        // some time later, after outputs have completed
        commitOffsetToKafkaTopic(meetupStream);
        context.start();
        try {
            context.awaitTermination();
        } catch (InterruptedException e) {
            logger.error("Error occurred while closing the Spark JavaStreamingContext");
        }
    }

    private JavaInputDStream<ConsumerRecord<String, MeetupRSVP>> createStream() {
        return KafkaUtils.createDirectStream(context, LocationStrategies.PreferConsistent(),
            ConsumerStrategies.Subscribe(fetchKafkaTopics(), initializeKafkaParameters()));
    }

    private Collection<String> fetchKafkaTopics() {
        return Collections.unmodifiableList(Collections.singletonList(kafkaPropertiesConfig.getTopic()));
    }

    private Map<String, Object> initializeKafkaParameters() {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertiesConfig.getBootstrapServers());
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomMeetupRSVPDeserializer.class);
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaPropertiesConfig.getGroupId());
        kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaPropertiesConfig.getAutoOffsetReset());
        kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return Collections.unmodifiableMap(kafkaParams);
    }

    private void updateVenueFrequency(JavaDStream<MeetupRSVP> meetupStreamValues) {
        JavaDStream<Venue> venues = meetupStreamValues.map(MeetupRSVP::getVenue).filter(Objects::nonNull);
        JavaPairDStream<Venue, Integer> venueCountPair = venues.mapToPair(venue -> new Tuple2<>(venue, 1))
                .reduceByKey(Integer::sum);
        venueCountPair.foreachRDD(javaRdd -> {
            Map<Venue, Integer> venueCountMap = javaRdd.collectAsMap();
            List<VenueFrequency> venueFrequencyList = venueCountMap.entrySet().stream()
                    .map(this::buildVenueFrequency).collect(Collectors.toList());
            saveRDDtoDatabase(venueFrequencyList);
        });
    }

    private VenueFrequency buildVenueFrequency(Map.Entry<Venue, Integer> entry) {
        return VenueFrequency.builder()
            .venue_id(entry.getKey().getVenue_id())
            .venue_name(entry.getKey().getVenue_name())
            .lat(entry.getKey().getLat())
            .lon(entry.getKey().getLon())
            .count(entry.getValue())
            .build();
    }

    private void saveRDDtoDatabase(List<VenueFrequency> venueFrequencyList) {
        JavaRDD<VenueFrequency> rdd = context.sparkContext()
            .parallelize(venueFrequencyList);
        javaFunctions(rdd).writerBuilder(
            cassandraPropertiesConfig.getKeyspaceName(),
            cassandraPropertiesConfig.getTableName(),
            mapToRow(VenueFrequency.class)).saveToCassandra();
    }

    private void commitOffsetToKafkaTopic(JavaInputDStream<ConsumerRecord<String, MeetupRSVP>> meetupStream) {
        meetupStream.foreachRDD((JavaRDD<ConsumerRecord<String, MeetupRSVP>> meetupRDD) -> {
            OffsetRange[] offsetRanges = ((HasOffsetRanges) meetupRDD.rdd()).offsetRanges();

            ((CanCommitOffsets) meetupStream.inputDStream())
                    .commitAsync(offsetRanges, commitCallback);
        });
    }

}
