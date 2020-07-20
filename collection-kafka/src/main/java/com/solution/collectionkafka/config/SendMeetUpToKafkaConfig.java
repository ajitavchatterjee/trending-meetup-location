package com.solution.collectionkafka.config;

import com.solution.collectionkafka.producer.KafkaMeetUpProducer;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SendMeetUpToKafkaConfig {

    private final KafkaMeetUpProducer kafkaMeetUpProducer;

    @Bean
    public ApplicationRunner loadMessage(){
        return args -> kafkaMeetUpProducer.readRSVPMeetupJson();
    }

}
