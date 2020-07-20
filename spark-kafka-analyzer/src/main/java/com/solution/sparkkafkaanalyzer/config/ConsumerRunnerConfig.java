package com.solution.sparkkafkaanalyzer.config;

import com.solution.sparkkafkaanalyzer.consumer.KafkaSparkMeetUpConsumer;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ConsumerRunnerConfig {

    private final KafkaSparkMeetUpConsumer kafkaSparkMeetUpConsumer;

    @Bean
    public ApplicationRunner loadMessage(){
        return args -> kafkaSparkMeetUpConsumer.getMeetUpData();
    }
}
