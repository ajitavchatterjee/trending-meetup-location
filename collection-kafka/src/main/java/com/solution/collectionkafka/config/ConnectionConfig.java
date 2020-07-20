package com.solution.collectionkafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "connection")
@Getter
@Setter
public class ConnectionConfig {
    private String url;
    private int timeout;
    private int readTimeout;
}
