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

/**
 * The Class KafkaSparkMeetUpConsumer reads the kafka topic and process them as
 * streams of batched with the help of spark. After processing, it saves only
 * the relevant part of the data into persistence storage (here, Cassandra).
 */
@Service
public class KafkaSparkMeetUpConsumer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaSparkMeetUpConsumer.class);

	private final CassandraPropertiesConfig cassandraPropertiesConfig;

	private final KafkaPropertiesConfig kafkaPropertiesConfig;

	private final JavaStreamingContext context;

	private final MeetupOffsetCommitCallback commitCallback;

	/**
	 * Instantiates a new kafka spark meet up consumer.
	 *
	 * @param cassandraPropertiesConfig the cassandra properties config
	 * @param kafkaPropertiesConfig     the kafka properties config
	 * @param context                   the context
	 * @param commitCallback            the commit callback
	 */
	@Autowired
	public KafkaSparkMeetUpConsumer(CassandraPropertiesConfig cassandraPropertiesConfig,
			KafkaPropertiesConfig kafkaPropertiesConfig, JavaStreamingContext context,
			MeetupOffsetCommitCallback commitCallback) {
		this.cassandraPropertiesConfig = cassandraPropertiesConfig;
		this.kafkaPropertiesConfig = kafkaPropertiesConfig;
		this.context = context;
		this.commitCallback = commitCallback;
	}

	/**
	 * Gets the meet up data.
	 *
	 * @return the meet up data
	 */
	public void getMeetUpData() {
		logger.debug("Enter into the getMeetUpData to start processing data");
		final JavaInputDStream<ConsumerRecord<String, MeetupRSVP>> meetupStream = createStream();
		final JavaDStream<MeetupRSVP> meetupStreamValues = meetupStream.map(ConsumerRecord::value);

		updateVenueFrequency(meetupStreamValues);
		commitOffsetToKafkaTopic(meetupStream);
		try {
			context.start();
			context.awaitTermination();
		} catch (InterruptedException e) {
			logger.error("Execution of JavaStreaming Context stopped unexpectedly due to {}", e.getMessage());
			context.ssc().sc().cancelAllJobs();
			context.stop(true, true);
		}
	}

	/**
	 * Creates the stream.
	 *
	 * @return the java input Dstream
	 */
	private JavaInputDStream<ConsumerRecord<String, MeetupRSVP>> createStream() {
		logger.debug("Direct stream is created on the topic :: {}", fetchKafkaTopics());
		return KafkaUtils.createDirectStream(context, LocationStrategies.PreferConsistent(),
				ConsumerStrategies.Subscribe(fetchKafkaTopics(), initializeKafkaParameters()));
	}

	/**
	 * Fetch kafka topics.
	 *
	 * @return the collection
	 */
	private Collection<String> fetchKafkaTopics() {
		return Collections.unmodifiableList(Collections.singletonList(kafkaPropertiesConfig.getTopic()));
	}

	/**
	 * Initialize kafka parameters.
	 *
	 * @return the map
	 */
	private Map<String, Object> initializeKafkaParameters() {
		Map<String, Object> kafkaParams = new HashMap<>();
		kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertiesConfig.getBootstrapServer());
		kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomMeetupRSVPDeserializer.class);
		kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaPropertiesConfig.getGroupId());
		kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaPropertiesConfig.getAutoOffsetReset());
		kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		logger.debug("Kafka parameters initialized successfully: {}", kafkaParams);
		return Collections.unmodifiableMap(kafkaParams);
	}

	/**
	 * Update venue frequency.
	 *
	 * @param meetupStreamValues the meetup stream values
	 */
	private void updateVenueFrequency(JavaDStream<MeetupRSVP> meetupStreamValues) {
		JavaDStream<Venue> venues = meetupStreamValues.map(MeetupRSVP::getVenue).filter(Objects::nonNull);
		JavaPairDStream<Venue, Integer> venueCountPair = venues.mapToPair(venue -> new Tuple2<>(venue, 1))
				.reduceByKey(Integer::sum);
		venueCountPair.foreachRDD(javaRdd -> {
			Map<Venue, Integer> venueCountMap = javaRdd.collectAsMap();
			logger.debug("Data in the venue count map: {}", venueCountMap.size());
			List<VenueFrequency> venueFrequencyList = venueCountMap.entrySet().stream().map(this::buildVenueFrequency)
					.collect(Collectors.toList());
			saveRDDtoDatabase(venueFrequencyList);
		});
	}

	/**
	 * Builds the venue frequency.
	 *
	 * @param entry the entry
	 * @return the venue frequency
	 */
	private VenueFrequency buildVenueFrequency(Map.Entry<Venue, Integer> entry) {
		return VenueFrequency.builder().venue_id(entry.getKey().getVenue_id())
				.venue_name(entry.getKey().getVenue_name()).lat(entry.getKey().getLat()).lon(entry.getKey().getLon())
				.count(entry.getValue()).build();
	}

	/**
	 * Save RD dto database.
	 *
	 * @param venueFrequencyList the venue frequency list
	 */
	private void saveRDDtoDatabase(List<VenueFrequency> venueFrequencyList) {
		logger.debug("Data saved into the database (cassandra):: {}", venueFrequencyList);
		JavaRDD<VenueFrequency> rdd = context.sparkContext().parallelize(venueFrequencyList);
		javaFunctions(rdd).writerBuilder(cassandraPropertiesConfig.getKeyspaceName(),
				cassandraPropertiesConfig.getTableName(), mapToRow(VenueFrequency.class)).saveToCassandra();
	}

	/**
	 * Commit offset to kafka topic.
	 *
	 * @param meetupStream the meetup stream
	 */
	private void commitOffsetToKafkaTopic(JavaInputDStream<ConsumerRecord<String, MeetupRSVP>> meetupStream) {
		meetupStream.foreachRDD((JavaRDD<ConsumerRecord<String, MeetupRSVP>> meetupRDD) -> {
			OffsetRange[] offsetRanges = ((HasOffsetRanges) meetupRDD.rdd()).offsetRanges();
			((CanCommitOffsets) meetupStream.inputDStream()).commitAsync(offsetRanges, commitCallback);
		});
	}

}
