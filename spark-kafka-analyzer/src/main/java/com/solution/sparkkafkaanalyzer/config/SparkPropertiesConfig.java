package com.solution.sparkkafkaanalyzer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

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