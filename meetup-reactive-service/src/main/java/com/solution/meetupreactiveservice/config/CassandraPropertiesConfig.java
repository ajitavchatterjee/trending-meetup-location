package com.solution.meetupreactiveservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The CassandraPropertiesConfig is responsible to fetch the configuration
 * properties to use the cassandra database.
 */
@ConfigurationProperties(prefix = "cassandra")
@Getter
@Setter
public class CassandraPropertiesConfig {

    private String keyspaceName;
    private int port;
    private String contactPoints;
    private String basePackages;
    private String localDataCenter;
}
