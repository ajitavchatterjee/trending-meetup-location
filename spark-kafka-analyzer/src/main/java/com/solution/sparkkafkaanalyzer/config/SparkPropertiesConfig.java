package com.solution.sparkkafkaanalyzer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The SparkPropertiesConfig is responsible to fetch the configuration
 * properties to use the Apache spark with cassandara database.
 */
@ConfigurationProperties(prefix = "spark")
@Getter
@Setter
public class SparkPropertiesConfig {
    private String appName;
    private String master;
    private String dbIpType;
    private String dbIp;
    private String caseSensitive;
    private String portType;
    private String port;
}