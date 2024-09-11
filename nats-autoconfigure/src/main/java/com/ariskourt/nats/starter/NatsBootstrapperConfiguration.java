package com.ariskourt.nats.starter;

import com.ariskourt.nats.NatsClient;
import com.ariskourt.nats.configuration.ConsumerConfiguration;
import com.ariskourt.nats.configuration.NatsConsumerConfiguration;
import com.ariskourt.nats.configuration.NatsPushSubscriberConfiguration;
import com.ariskourt.nats.handler.NatsHandler;
import com.ariskourt.nats.starter.properties.NatsProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Set;

/**
 * Auto-configuration class for bootstrapping NATS connections and handlers.
 */
@AutoConfiguration
@EnableConfigurationProperties(NatsProperties.class)
@ConditionalOnProperty(prefix = "nats", name = "enabled", havingValue = "true", matchIfMissing = true)
public class NatsBootstrapperConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(NatsBootstrapperConfiguration.class);

    private final NatsClient natsClient;
    private final Set<NatsHandler<?>> handlers;

    /**
     * Constructor for creating an instance of NatsBootstrapperConfiguration.
     *
     * @param natsClient The NATS client
     * @param handlers A set of NATS handlers
     */
    public NatsBootstrapperConfiguration(NatsClient natsClient, Set<NatsHandler<?>> handlers) {
        this.natsClient = natsClient;
        this.handlers = handlers;
    }

    /**
     * Initializes the NATS connection and registers handlers after the bean is constructed.
     */
    @PostConstruct
    public void connect() {
        natsClient.connect();
        registerHandlers();
    }

    /**
     * Disconnects from the NATS server before the bean is destroyed.
     */
    @PreDestroy
    public void disconnect() {
        natsClient.disconnect();
    }

    /**
     * Registers the NATS handlers with the NATS server.
     * Throws an {@link IllegalStateException} if no suitable handler is found.
     */
    private void registerHandlers() {
        if (CollectionUtils.isNotEmpty(handlers)) {
            handlers.forEach(handler -> {
                var handlerClass = handler.getClass().getCanonicalName();
                if (CollectionUtils.isEmpty(handler.filterSubjects())) {
                    LOGGER.error("No NATS subject value found for handler of type {} and registered filterSubject {}", handlerClass, handler.filterSubjects());
                    throw new IllegalStateException(String.format("No suitable handler found for type %s and filterSubject %s", handler, handler.filterSubjects()));
                }
                LOGGER.debug("Registering NATS handler {} using filterSubject {}", handlerClass, handler.filterSubjects());
                var configuration = NatsConsumerConfiguration.builder()
                        .with(builder -> builder.setConsumerConfiguration(ConsumerConfiguration.builder()
                                .with(consumerBuilder -> {
                                    consumerBuilder.setDurable(handler.durable());
                                    consumerBuilder.setFilterSubjects(handler.filterSubjects());
                                    consumerBuilder.setMaxDeliver(handler.maxDeliver());
                                    consumerBuilder.setAckAwait(handler.ackWait());
                                }).build())
                        ).with(builder -> builder.setPushSubscriberConfiguration(NatsPushSubscriberConfiguration.builder()
                                .with(pushBuilder -> {
                                    pushBuilder.setName(handler.consumerName());
                                    pushBuilder.setDeliverGroup(handler.deliverGroup());
                                    pushBuilder.setDeliverSubject(handler.deliverSubject());
                                }).build()
                        )).build();
                natsClient.subscribePushConsumer(configuration, handler);
            });
        }
    }

}
