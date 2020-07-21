package com.solution.sparkkafkaanalyzer;

import com.solution.sparkkafkaanalyzer.config.CassandraPropertiesConfig;
import com.solution.sparkkafkaanalyzer.config.KafkaPropertiesConfig;
import com.solution.sparkkafkaanalyzer.config.SparkPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * The Class SparkKafkaAnalyzerApplication is responsible to invoke the
 * application to process the meetup data and save the processed data inside
 * cassandra database.
 */
@SpringBootApplication
@EnableConfigurationProperties(
	{
		SparkPropertiesConfig.class,
		CassandraPropertiesConfig.class,
		KafkaPropertiesConfig.class
	})
public class SparkKafkaAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparkKafkaAnalyzerApplication.class, args);
	}
}
