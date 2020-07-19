package com.solution.sparkkafkaanalyzer.consumer;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

import com.mongodb.spark.MongoSpark;
import com.solution.sparkkafkaanalyzer.config.CustomDeserializer;
import com.solution.sparkkafkaanalyzer.encoder.MeetUpRSVPEncoder;
import com.solution.sparkkafkaanalyzer.model.MeetupRSVP;
import com.solution.sparkkafkaanalyzer.model.Venue;
import com.solution.sparkkafkaanalyzer.model.VenueFrequency;
import kafka.serializer.StringDecoder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.*;

@Service
public class KafkaMeetUpConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMeetUpConsumer.class);

    private static final String MEETUP_VIEW = "meetup_view";

    @Value("${kafka.topic}")
    private String topic;

    private final JavaStreamingContext context;

    private final SparkSession session;

    private final MeetupOffsetCommitCallback commitCallback;

    @Autowired
    public KafkaMeetUpConsumer(JavaStreamingContext context, SparkSession session, MeetupOffsetCommitCallback commitCallback) {
        this.context = context;
        this.session = session;
        this.commitCallback = commitCallback;
    }

    public void getMeetUpData() throws InterruptedException {
        final JavaInputDStream<ConsumerRecord<String, MeetupRSVP>> meetupStream = createStream();
        final JavaDStream<MeetupRSVP> meetupStreamValues = meetupStream.map(ConsumerRecord::value);
//        meetupStream.print();
        JavaDStream<Venue> venues = meetupStreamValues.map(MeetupRSVP::getVenue).filter(Objects::nonNull);
        JavaPairDStream<Venue, Integer> venueCounts = venues.mapToPair(s -> new Tuple2<>(s, 1))
            .reduceByKey(Integer::sum);
//        venueCounts.print();
        venueCounts.foreachRDD(javaRdd -> {
            Map<Venue, Integer> wordCountMap = javaRdd.collectAsMap();
            for (Venue key : wordCountMap.keySet()) {
                    List<VenueFrequency> venueFrequencyList =
                            Collections.singletonList(VenueFrequency.builder()
                                    .venue_id(key.getVenue_id())
                                    .venue_name(key.getVenue_name())
                                    .lat(key.getLat())
                                    .lon(key.getLon())
                                    .count(wordCountMap.get(key))
                                    .build()
                            );
//                Dataset<VenueFrequency> datasetWrite = session.createDataset(venueFrequencyList, Encoders.bean(VenueFrequency.class));
//                //Save data to Cassandra
//                datasetWrite.write().format("org.apache.spark.sql.cassandra").options(new HashMap<String, String>() {
//                    {
//                        put("keyspace", "rsvp");
//                        put("table", "meetupfrequenncy");
//                    }
//                }).mode(SaveMode.Append).save();
                    JavaRDD<VenueFrequency> rdd = context.sparkContext()
                            .parallelize(venueFrequencyList);
                    javaFunctions(rdd).writerBuilder("rsvp", "meetupfrequenncy", mapToRow(VenueFrequency.class))
                            .saveToCassandra();
                }
        });
//        meetupStreamValues.print();
//        meetupStreamValues.foreachRDD( meetupRDD ->
//            {
//                if (!meetupRDD.isEmpty()) {
//                    Dataset<Row> row = session.read()
//                        .json(session.createDataset(meetupRDD.rdd(), Encoders.STRING()));
//                    row.printSchema();
//                    row.createOrReplaceTempView(MEETUP_VIEW);
//                    Dataset<Row> auMeetups = session.sql("select * from " + MEETUP_VIEW);
//                    System.out.println("=====================================STRAAAAARTTT================================");
//                            auMeetups.show();
//                    System.out.println("=====================================ENDDDDDDDDD================================");
//                    MongoSpark.save(auMeetups);
//                }
//            }
//        );
//        meetupStreamValues.map(record ->(record.value().toString)).print();
//        meetupStreamValues.foreachRDD(
//            rdd -> {
//                if(!rdd.isEmpty()) {
//                    rdd.collect().forEach(t ->
//                            System.out.println("=========================================here==============================================:" + t)
//                    );
//                } else {
//                    System.out.println("=========================================EMPTYYYYYYYYYYYYYYY==============================================:" );
//                }
//            }
//        );

        // some time later, after outputs have completed
        meetupStream.foreachRDD((JavaRDD<ConsumerRecord<String, MeetupRSVP>> meetupRDD) -> {
            OffsetRange[] offsetRanges = ((HasOffsetRanges) meetupRDD.rdd()).offsetRanges();

            ((CanCommitOffsets) meetupStream.inputDStream())
                    .commitAsync(offsetRanges);
        });
        context.start();
        context.awaitTermination();
    }

    private JavaInputDStream<ConsumerRecord<String, MeetupRSVP>> createStream() {
        return KafkaUtils.createDirectStream(context, LocationStrategies.PreferConsistent(),
            ConsumerStrategies.Subscribe(fetchKafkaTopics(), initializeKafkaParameters()));
    }

    private Collection<String> fetchKafkaTopics() {
        return Collections.unmodifiableList(Collections.singletonList(topic));
    }

    private Map<String, Object> initializeKafkaParameters() {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializer.class);
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, "meetupGroup");
        kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return Collections.unmodifiableMap(kafkaParams);
    }

}
