package com.solution.collectionkafka;

import com.solution.collectionkafka.config.ConnectionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConnectionConfig.class)
public class CollectionKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectionKafkaApplication.class, args);
	}

}
