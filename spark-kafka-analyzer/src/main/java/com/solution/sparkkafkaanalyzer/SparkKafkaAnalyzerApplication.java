package com.solution.sparkkafkaanalyzer;

import com.solution.sparkkafkaanalyzer.consumer.KafkaMeetUpConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SparkKafkaAnalyzerApplication {

	@Autowired
	private KafkaMeetUpConsumer kafkaMeetUpConsumer;

	public static void main(String[] args) {
		SpringApplication.run(SparkKafkaAnalyzerApplication.class, args);
	}

	@Bean
	public ApplicationRunner loadMessage(){
		return args ->{kafkaMeetUpConsumer.getMeetUpData();};
	}

}
