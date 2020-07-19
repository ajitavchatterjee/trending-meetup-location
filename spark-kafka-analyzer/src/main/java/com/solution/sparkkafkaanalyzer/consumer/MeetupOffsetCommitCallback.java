package com.solution.sparkkafkaanalyzer.consumer;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MeetupOffsetCommitCallback implements OffsetCommitCallback {

    private static final Logger logger = LoggerFactory.getLogger(MeetupOffsetCommitCallback.class);

    @Override
    public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
//        logger.info("---------------------------------------------------");
//        logger.log(Level.INFO, "{0} | {1}", new Object[] { offsets, exception });
//        logger.info("---------------------------------------------------");
    }
}
