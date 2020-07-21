package com.solution.sparkkafkaanalyzer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The KafkaPropertiesConfig is responsible to fetch the configuration
 * properties to use the kafka.
 */
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaPropertiesConfig {

    private String topic;
    private String bootstrapServer;
    private String groupId;
    private String autoOffsetReset;
}
