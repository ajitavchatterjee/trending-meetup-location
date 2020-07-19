package com.solution.collectionkafka.config;

import com.solution.collectionkafka.producer.KafkaMeetUpProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendMeetUpMessage {

    @Autowired
    private KafkaMeetUpProducer kafkaMeetUpProducer;

    @Bean
    public ApplicationRunner loadMessage(){
        return args ->{kafkaMeetUpProducer.readMeetupJson();};
    }

}
