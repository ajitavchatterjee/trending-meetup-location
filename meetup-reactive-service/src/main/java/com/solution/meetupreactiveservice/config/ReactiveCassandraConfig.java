package com.solution.meetupreactiveservice.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Collections;
import java.util.List;

/**
 * The CassandraPropertiesConfig provides all the standard setup that to
 * configure Reactive Cassandra database and also, creating a ReactiveSession
 * and ReactiveCassandraTemplate
 */
@Configuration
@EnableReactiveCassandraRepositories
@EnableConfigurationProperties(CassandraPropertiesConfig.class)
@AllArgsConstructor
public class ReactiveCassandraConfig extends AbstractReactiveCassandraConfiguration {

	private final CassandraPropertiesConfig cassandraPropertiesConfig;

	/**
	 * Gets the keyspace name.
	 *
	 * @return the keyspace name
	 */
	@Override
	protected String getKeyspaceName() {
		return cassandraPropertiesConfig.getKeyspaceName();
	}

	/**
	 * Gets the contact points.
	 *
	 * @return the contact points
	 */
	@Override
	protected String getContactPoints() {
		return cassandraPropertiesConfig.getContactPoints();
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	@Override
	protected int getPort() {
		return cassandraPropertiesConfig.getPort();
	}

	/**
	 * Gets the schema action.
	 *
	 * @return the schema action
	 */
	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	/**
	 * Gets the entity base packages.
	 *
	 * @return the entity base packages
	 */
	@Override
	public String[] getEntityBasePackages() {
		return new String[] { cassandraPropertiesConfig.getBasePackages() };
	}

	/**
	 * Gets the local data center.
	 *
	 * @return the local data center
	 */
	@Override
	protected String getLocalDataCenter() {
		return cassandraPropertiesConfig.getLocalDataCenter();
	}

	/**
	 * Gets the keyspace creations.
	 *
	 * @return the keyspace creations
	 */
	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		final CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
				.createKeyspace(cassandraPropertiesConfig.getKeyspaceName()).ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
		return Collections.singletonList(specification);
	}

}
