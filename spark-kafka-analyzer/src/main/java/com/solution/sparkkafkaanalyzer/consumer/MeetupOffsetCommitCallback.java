package com.solution.sparkkafkaanalyzer.consumer;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * The Class MeetupOffsetCommitCallback is just an implementation of the
 * callback interface that can be used to trigger custom actions when a commit
 * request completes
 */
@Component
public class MeetupOffsetCommitCallback implements OffsetCommitCallback {

	private static final Logger logger = LoggerFactory.getLogger(MeetupOffsetCommitCallback.class);

	/**
	 * It logs the offset details or any exception that happens after
	 * committing the offset
	 *
	 * @param map the map
	 * @param e   the e
	 */
	@Override
	public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
		logger.debug("Offset:: {} and  Exception {}", map, e);
	}
}
