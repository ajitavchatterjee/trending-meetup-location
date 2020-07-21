package com.solution.collectionkafka.producer;

import com.solution.collectionkafka.config.ConnectionConfig;
import com.solution.collectionkafka.utils.LoggerMessageUtils;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The Class KafkaMeetUpProducer is responsible for reading the data as input
 * steam and sends it to kafka topic.
 */
@Component
@EnableBinding(Source.class)
public class KafkaMeetUpProducer {

	private final ConnectionConfig connectionConfig;

	@Getter
	private final Source source;

	private static final Logger logger = LoggerFactory.getLogger(KafkaMeetUpProducer.class);

	@Value("${kafka.timeout}")
	private int sendMessageTimeout;

	/**
	 * Instantiates a new kafka meet up producer.
	 *
	 * @param connectionConfig the connection config
	 * @param source           the source
	 */
	@Autowired
	public KafkaMeetUpProducer(ConnectionConfig connectionConfig, Source source) {
		this.connectionConfig = connectionConfig;
		this.source = source;
	}

	/**
	 * Read RSVP meetup json.
	 */
	public void readRSVPMeetupJson() {
		HttpURLConnection con = null;
		try {
			URL url = new URL(connectionConfig.getUrl());
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(connectionConfig.getTimeout());
			con.setReadTimeout(connectionConfig.getReadTimeout());
			con.setRequestMethod("GET");
			logger.debug(LoggerMessageUtils.CONNECTION_SUCCESS_STATUS_MSG, con.getResponseCode());
			readInputStream(con);
		} catch (MalformedURLException e) {
			logger.error(LoggerMessageUtils.ERROR_INCORRECT_URL_MSG, connectionConfig.getUrl());
		} catch (IOException e) {
			logger.error(LoggerMessageUtils.CONNECTION_FAILURE_MSG, connectionConfig.getUrl());
		} finally {
			if (con != null)
				con.disconnect();
		}
	}

	/**
	 * Read input stream.
	 *
	 * @param con the con
	 */
	private void readInputStream(HttpURLConnection con) {
		try {
			BufferedReader in;
			String inputLine;
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				logger.debug(LoggerMessageUtils.INCOMING_JSON_MSG, inputLine);
				sendToKafkaTopic(inputLine);
			}
			in.close();
		} catch (IOException e) {
			logger.error(LoggerMessageUtils.INPUT_STREAM_ERROR_MSG);
		}

	}

	/**
	 * Send to kafka topic.
	 *
	 * @param inputLine the input line
	 */
	private void sendToKafkaTopic(String inputLine) {
		source.output().send(MessageBuilder.withPayload(inputLine).build(), sendMessageTimeout);
		logger.debug("Message : {} sent successfully to kafka", inputLine);
	}
}
