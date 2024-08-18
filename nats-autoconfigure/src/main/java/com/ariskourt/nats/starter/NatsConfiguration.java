package com.ariskourt.nats.starter;

import com.ariskourt.nats.NatsClient;
import com.ariskourt.nats.configuration.NatsConnectionConfiguration;
import com.ariskourt.nats.configuration.NatsConnectionConfigurationParameters;
import com.ariskourt.nats.starter.exception.NatsConfigurationException;
import com.ariskourt.nats.starter.properties.NatsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;

/**
 * Auto-configuration class for setting up NATS connections and related properties.
 */
@AutoConfiguration
@ConditionalOnClass(NatsClient.class)
@EnableConfigurationProperties(NatsProperties.class)
public class NatsConfiguration {

    private final NatsProperties properties;
    private ExecutorService natsExecutorService;
    public void setNatsExecutorService(@Autowired(required = false) ExecutorService natsExecutorService) {
        this.natsExecutorService = natsExecutorService;
    }

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

    /**
     * Creates the NATS connection configuration based on the provided properties.
     *
     * @return the NATS connection configuration
     * @throws NatsConfigurationException if any required property is missing
     */
    private NatsConnectionConfiguration configuration() {
        var configuration = new NatsConnectionConfiguration();
        if (properties.urls() == null) {
            throw new NatsConfigurationException("NATS connection URLs cannot be null!");
        }
        configuration.put(NatsConnectionConfigurationParameters.NATS_CONNECTION_URLS, properties.urls());
        if (properties.maxReconnects() == null) {
            throw new NatsConfigurationException("NATS connection max reconnects cannot be null!");
        }
        configuration.put(NatsConnectionConfigurationParameters.NATS_CONNECTION_MAX_RECONNECTS, properties.maxReconnects());
        configuration.put(NatsConnectionConfigurationParameters.NATS_CONNECTION_TRACE_CONNECTION, properties.traceConnection() != null && properties.traceConnection());
        if (properties.drainAwaitSeconds() == null) {
            throw new NatsConfigurationException("NATS connection drain await seconds cannot be null!");
        }
        configuration.put(NatsConnectionConfigurationParameters.NATS_CONNECTION_DRAIN_AWAIT_SECONDS, properties.drainAwaitSeconds());
        configuration.put(NatsConnectionConfigurationParameters.NATS_CONNECTION_USE_DISPATCHER_WITH_EXECUTOR, properties.useDispatcherWithExecutor() != null && properties.useDispatcherWithExecutor());
        if ((properties.useDispatcherWithExecutor() != null && properties.useDispatcherWithExecutor()) && natsExecutorService == null) {
            throw new NatsConfigurationException("Cannot create a NATS connection with a dispatcher and executor service without providing an executor service!");
        }
        if (natsExecutorService != null) {
            configuration.put(NatsConnectionConfigurationParameters.NATS_CONNECTION_EXECUTOR_SERVICE, natsExecutorService);
        }
        return configuration;
    }

}
