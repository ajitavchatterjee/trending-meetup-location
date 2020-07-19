package com.solution.meetupreactiveservice.config;

import org.springframework.beans.factory.annotation.Value;
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
public class ReactiveCassandraConfig extends AbstractReactiveCassandraConfiguration {

    @Value("${cassandra.keySpaceName}")
    private String keySpaceName;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.contactPoints}")
    private String contactPoints;

    @Value("${cassandra.basePackages}")
    private String basePackages;

    @Value("${cassandra.localDataCenter}")
    private String dataCenter;


    @Override
    protected String getKeyspaceName() {
        return keySpaceName;
    }

    @Override protected String getContactPoints() {
        return contactPoints;
    }

    @Override protected int getPort() {
        return port;
    }

    @Override public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {basePackages};
    }

    @Override
    protected String getLocalDataCenter() {
        return dataCenter;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keySpaceName)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return Collections.singletonList(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return Collections.singletonList(DropKeyspaceSpecification.dropKeyspace(keySpaceName));
    }

}
