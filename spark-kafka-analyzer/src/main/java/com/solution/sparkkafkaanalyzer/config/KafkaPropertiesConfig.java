package com.solution.sparkkafkaanalyzer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cassandra")
@Getter
@Setter
public class KafkaPropertiesConfig {

    private String topic;
    private String bootstrapServers;
    private String groupId;
    private String autoOffsetReset;
}
