package com.solution.sparkkafkaanalyzer.config;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The SparkConfig is responsible to create the JavaSteaming context to start
 * the batch processing from kafka topic.
 */
@Configuration
@AllArgsConstructor
public class SparkConfig {

	/** The spark properties config. */
	private final SparkPropertiesConfig sparkPropertiesConfig;

	/**
	 * Configures the Spark
	 *
	 * @return the spark conf instance
	 */
	@Bean
	public SparkConf conf() {
		return new SparkConf().setAppName(sparkPropertiesConfig.getAppName())
				.setMaster(sparkPropertiesConfig.getMaster())
				.set(sparkPropertiesConfig.getDbIpType(), sparkPropertiesConfig.getDbIp())
				.set(sparkPropertiesConfig.getPortType(), sparkPropertiesConfig.getPort())
				.set("spark.serializer", "org.apache.spark.serializer.KryoSerialize")
				.set("spark.sql.caseSensitive", sparkPropertiesConfig.getCaseSensitive())
				.registerKryoClasses(new Class<?>[] { ConsumerRecord.class });
	}

	/**
	 * Creates the java streaming context.
	 *
	 * @return the java streaming context
	 */
	@Bean
	public JavaStreamingContext createJavaStreamingContext() {
		return new JavaStreamingContext(conf(), Durations.seconds(1));
	}
}
