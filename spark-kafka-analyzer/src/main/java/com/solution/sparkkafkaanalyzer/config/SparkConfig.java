package com.solution.sparkkafkaanalyzer.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SparkConfig {

    @Value("${spark.app.name}")
    private String appName;

    @Value("${spark.master}")
    private String masterUri;

    @Value("${spark.dbIpType}")
    private String dbIpType;

    @Value("${spark.dbIp}")
    private String dbIp;

    @Value("${spark.caseSensitive}")
    private String caseSensitive;

    @Bean
    public SparkConf conf() {
        return new SparkConf()
            .setAppName(appName)
            .setMaster(masterUri)
//            .set("spark.cassandra.connection.host", dbIp)
//                .set(dbIpType, dbIp)
            .set("spark.cassandra.connection.host", "127.0.0.1") //TODO:: Cassandra configure
            .set("spark.serializer","org.apache.spark.serializer.KryoSerialize")
            .set("spark.sql.caseSensitive", caseSensitive)
            .registerKryoClasses(new Class<?>[]{ ConsumerRecord.class });
    }
//    conf.set("spark.serializer","org.apache.spark.serializer.KryoSerialize")
//            conf.registerKryoClasses(util.Arrays.asList(classOf[ConsumerRecord[_, _]]).toArray.asInstanceOf[Array[Class[_]]])

    @Bean
    public JavaStreamingContext sc() {
        return new JavaStreamingContext(conf(), Durations.seconds(1));
    }

    @Bean
    public SparkSession buildSparkSession() {
        return SparkSession.builder().config(conf()).getOrCreate();
    }
}
