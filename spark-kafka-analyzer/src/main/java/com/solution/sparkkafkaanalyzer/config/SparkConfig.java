package com.solution.sparkkafkaanalyzer.config;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@AllArgsConstructor
public class SparkConfig {

    private final SparkPropertiesConfig sparkPropertiesConfig;

    @Bean
    public SparkConf conf() {
        return new SparkConf()
            .setAppName(sparkPropertiesConfig.getAppName())
            .setMaster(sparkPropertiesConfig.getMaster())
            .set(sparkPropertiesConfig.getDbIpType(), sparkPropertiesConfig.getDbIp())
            .set(sparkPropertiesConfig.getPortType(), sparkPropertiesConfig.getPort())
            .set("spark.serializer","org.apache.spark.serializer.KryoSerialize")
            .set("spark.sql.caseSensitive", sparkPropertiesConfig.getCaseSensitive())
            .registerKryoClasses(new Class<?>[]{ ConsumerRecord.class });
    }

    @Bean
    public JavaStreamingContext createJavaStreamingContext() {
        return new JavaStreamingContext(conf(), Durations.seconds(1));
    }
}
