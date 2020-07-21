package com.solution.collectionkafka.config;

import com.solution.collectionkafka.producer.KafkaMeetUpProducer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * The SendMeetUpToKafkaConfig invokes the Kafka producer to send the data
 * to the kafka topic.
 */
@Configuration
@AllArgsConstructor
public class SendMeetUpToKafkaConfig {
    private static final Logger logger = LoggerFactory.getLogger(SendMeetUpToKafkaConfig.class);

    private final KafkaMeetUpProducer kafkaMeetUpProducer;

    
    /**
     * Invokes kafka producer.
     *
     * @return the application runner
     */
    @Bean
    public ApplicationRunner invokeKafkaProducer(){
        logger.debug("Kafka producer has been invoked to send the input data.");
        return args -> kafkaMeetUpProducer.readRSVPMeetupJson();
    }

}
