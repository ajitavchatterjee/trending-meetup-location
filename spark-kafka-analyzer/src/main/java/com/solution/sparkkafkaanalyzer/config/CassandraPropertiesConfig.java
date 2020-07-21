package com.solution.sparkkafkaanalyzer.config;

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
	private String tableName;
}
