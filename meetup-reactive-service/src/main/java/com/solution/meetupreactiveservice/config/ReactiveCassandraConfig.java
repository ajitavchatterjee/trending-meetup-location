package com.solution.meetupreactiveservice.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableReactiveCassandraRepositories
@EnableConfigurationProperties(CassandraPropertiesConfig.class)
@AllArgsConstructor
public class ReactiveCassandraConfig extends AbstractReactiveCassandraConfiguration {

    private final CassandraPropertiesConfig cassandraPropertiesConfig;

    @Override
    protected String getKeyspaceName() {
        return cassandraPropertiesConfig.getKeyspaceName();
    }

    @Override protected String getContactPoints() {
        return cassandraPropertiesConfig.getContactPoints();
    }

    @Override protected int getPort() {
        return cassandraPropertiesConfig.getPort();
    }

    @Override public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {cassandraPropertiesConfig.getBasePackages()};
    }

    @Override
    protected String getLocalDataCenter() {
        return cassandraPropertiesConfig.getLocalDataCenter();
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(cassandraPropertiesConfig.getKeyspaceName())
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return Collections.singletonList(specification);
    }

}
