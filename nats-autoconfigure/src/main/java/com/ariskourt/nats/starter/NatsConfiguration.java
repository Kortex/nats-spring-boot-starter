package com.ariskourt.nats.starter;

import com.ariskourt.nats.NatsClient;
import com.ariskourt.nats.configuration.NatsConnectionConfiguration;
import com.ariskourt.nats.configuration.NatsConnectionConfigurationParameters;
import com.ariskourt.nats.starter.exception.NatsConfigurationException;
import com.ariskourt.nats.starter.properties.NatsProperties;
import io.nats.client.Connection;
import io.nats.client.JetStream;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto-configuration class for setting up NATS connections and related properties.
 */
@AutoConfiguration
@ConditionalOnClass(NatsClient.class)
@EnableConfigurationProperties(NatsProperties.class)
@ConditionalOnProperty(prefix = "nats", name = "enabled", havingValue = "true", matchIfMissing = true)
public class NatsConfiguration {

    private final NatsProperties properties;

    /**
     * Constructor for creating an instance of NatsConfiguration.
     *
     * @param properties the properties for configuring the NATS connection
     */
    public NatsConfiguration(NatsProperties properties) {
        this.properties = properties;
    }

    /**
     * Creates a NATS bean if one is not already present in the context.
     *
     * @return a new instance of Nats
     */
    @Bean(name = "nats")
    @ConditionalOnMissingBean
    public NatsClient nats() {
        return new NatsClient(configuration());
    }

    @Bean(name = "natsConnection")
    @ConditionalOnMissingBean
    public Connection natsConnection() {
        return nats().getConnection();
    }

    @Bean(name = "natsJetStream")
    @ConditionalOnMissingBean
    public JetStream natsJetStream() {
        return nats().getJetStream();
    }

    /**
     * Creates the NATS connection configuration based on the provided properties.
     *
     * @return the NATS connection configuration
     * @throws NatsConfigurationException if any required property is missing
     */
    private NatsConnectionConfiguration configuration() {
        var configuration = new NatsConnectionConfiguration();
        if (properties.enabled() != null) {
            configuration.put(NatsConnectionConfigurationParameters.NATS_ENABLED, properties.enabled());
        }
        if (properties.urls() != null) {
            configuration.put(NatsConnectionConfigurationParameters.NATS_URLS, properties.urls());
        }
        if (properties.maxReconnects() != null) {
            configuration.put(NatsConnectionConfigurationParameters.NATS_MAX_RECONNECTS, properties.maxReconnects());
        }
        if (properties.traceConnection() != null) {
            configuration.put(NatsConnectionConfigurationParameters.NATS_TRACE_CONNECTION, properties.traceConnection());
        }
        if (properties.drainAwaitSeconds() != null) {
            configuration.put(NatsConnectionConfigurationParameters.NATS_DRAIN_AWAIT_SECONDS, properties.drainAwaitSeconds());
        }
        configuration.put(NatsConnectionConfigurationParameters.NATS_USE_DISPATCHER_WITH_EXECUTOR, BooleanUtils.isTrue(properties.useDispatcherWithExecutor()));
        if (BooleanUtils.isTrue(properties.useDispatcherWithExecutor())) {
            var executor = properties.executor();
            if (executor != null) {
                if (executor.poolSize() != null) {
                    configuration.put(NatsConnectionConfigurationParameters.NATS_EXECUTOR_POOL_SIZE, executor.poolSize());
                }
                if (executor.namingPrefix() != null) {
                    configuration.put(NatsConnectionConfigurationParameters.NATS_EXECUTOR_NAMING_PREFIX, executor.namingPrefix());
                }
            }
        }
        return configuration;
    }

}
