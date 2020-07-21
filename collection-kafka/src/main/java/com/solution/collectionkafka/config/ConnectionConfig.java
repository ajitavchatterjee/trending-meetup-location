package com.solution.collectionkafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * The ConnectionConfig class sets all the HTTP URL connection
 * related properties.
 */
@ConfigurationProperties(prefix = "connection")
@Getter
@Setter
public class ConnectionConfig {
    private String url;
    private int timeout;
    private int readTimeout;
}
