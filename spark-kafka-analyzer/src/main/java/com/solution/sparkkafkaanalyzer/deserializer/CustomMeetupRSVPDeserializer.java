package com.solution.sparkkafkaanalyzer.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.sparkkafkaanalyzer.model.MeetupRSVP;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * The Class CustomMeetupRSVPDeserializer is the custom deserializer while
 * consuming the CustomerRecords from the kafka topic.
 */
@Component
public class CustomMeetupRSVPDeserializer implements Deserializer<MeetupRSVP> {

	private static final Logger logger = LoggerFactory.getLogger(CustomMeetupRSVPDeserializer.class);

	/**
	 * Configure.
	 *
	 * @param configs the configs
	 * @param isKey   the is key
	 */
	@Override
	public void configure(Map configs, boolean isKey) {
	}

	/**
	 * Deserialize.
	 *
	 * @param s     the s
	 * @param bytes the bytes
	 * @return the meetup RSVP
	 */
	@Override
	public MeetupRSVP deserialize(String s, byte[] bytes) {
		return null;
	}

	/**
	 * Deserialize.
	 *
	 * @param topic   the topic
	 * @param headers the headers
	 * @param data    the data
	 * @return the meetup RSVP
	 */
	@Override
	public MeetupRSVP deserialize(String topic, Headers headers, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		MeetupRSVP mRSVP = null;
		try {
			mRSVP = mapper.readValue(data, MeetupRSVP.class);
		} catch (IOException e) {
			logger.error("Some issue occurred while deserializing the MeetupRSVP data: {}", data);
		}
		return mRSVP;
	}

	/**
	 * Close.
	 */
	@Override
	public void close() {
	}
}
