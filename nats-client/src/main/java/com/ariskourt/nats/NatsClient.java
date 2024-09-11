package com.ariskourt.nats;

import com.ariskourt.nats.configuration.NatsConnectionConfiguration;
import com.ariskourt.nats.configuration.NatsConsumerConfiguration;
import com.ariskourt.nats.exception.NatsException;
import com.ariskourt.nats.listener.DefaultNatsConnectionListener;
import com.ariskourt.nats.listener.DefaultNatsErrorListener;
import io.nats.client.*;
import io.nats.client.api.ConsumerConfiguration;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.*;

/**
 * The Nats class provides methods to connect, disconnect, and subscribe to NATS server.
 */
public class NatsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NatsClient.class);

    protected final ErrorListener errorListener;
    protected final ConnectionListener connectionListener;
    protected final NatsConnectionConfiguration configuration;
    protected final Map<String, Dispatcher> dispatchers = new ConcurrentHashMap<>();

    private Connection connection;

    /**
     * Gets the current NATS connection.
     *
     * @return the current NATS connection
     */
    public Connection getConnection() {
        return connection;
    }

    private JetStream jetStream;

    /**
     * Gets the current JetStream instance.
     *
     * @return the current JetStream instance
     */
    public JetStream getJetStream() {
        return jetStream;
    }

    /**
     * Constructor that creates a new instance of the {@link NatsClient} object using the provided configuration. This
     * @param configuration The configuration that will be used to create the connection to the NATS server
     */
    public NatsClient(NatsConnectionConfiguration configuration) {
        this(configuration, null, null);
    }

    /**
     * Constructor that creates a new instance of the {@link NatsClient} object using the provided configuration. This
     * constructor also allows for providing custom error and connection listeners that will be used to handle
     * errors and connection events respectively.
     * @param configuration The configuration that will be used to create the connection to the NATS server
     * @param errorListener The error listener that will be used to handle errors
     * @param connectionListener The connection listener that will be used to handle connection events
     */
    public NatsClient(NatsConnectionConfiguration configuration, ErrorListener errorListener, ConnectionListener connectionListener) {
        this.errorListener = errorListener == null ? new DefaultNatsErrorListener() : errorListener;
        this.connectionListener = connectionListener == null ? new DefaultNatsConnectionListener() : connectionListener;
        this.configuration = configuration;
    }

    /**
     * This method is responsible for creating a connection to the NATS server using the provided configuration. This
     * method will attempt to establish a connection to the NATS server using the provided configuration. In case the
     * connection process fails, the method will throw a {@link NatsException} with the underlying cause of the failure.
     *
     * @return The current instance of the {@link NatsClient} object
     */
    public NatsClient connect() {
        try {
            LOGGER.info("Attempting to connect to NATS server using the following configuration {}", configuration);
            connection = io.nats.client.Nats.connect(createOptions());
            jetStream = connection.jetStream();
        } catch (IOException | InterruptedException e) {
            throw new NatsException("An error occurred while trying to connect to the NATS server!", e);
        }
        return this;
    }

    /**
     * This method can be called to disconnect from the already connected NATS server. This method can be used for
     * shutting down existing connection to avoid lingering resources, for example during application shutdown. This
     * method will attempt to close an active connection in case the following criteria are met:
     * <ol>
     *     <li>The connection object is not null</li>
     *     <li>The connection is not null and is currently in the CONNECTED state</li>
     * </ol>
     *
     * @throws NatsException In case the process of shutting down the connection has failed
     */
    public NatsClient disconnect() {
        if (connection == null) {
            return this;
        }
        if (Connection.Status.CONNECTED != connection.getStatus()) {
            LOGGER.warn("Active connection is not in connected status. Nothing to do...");
            return this;
        }
        try {
            var drained = connection.drain(Duration.ofSeconds(configuration.getDrainAwaitSeconds())).get();
            if (drained) {
                LOGGER.info("Successfully drained NATS connection before closing...");
            }
            closeDispatchers();
            connection.close();
            LOGGER.info("NATS connection has been closed successfully");
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.error("Closing NATS connection has failed", e);
            throw new NatsException(e);
        }
        return this;
    }

    /**
     * Method that allows for registering push consumers as dispatchers for a given NATS subject. This method
     * expects a non-null topic value as we well as a non-null handler instance. This method will throw
     * an NPE in case the passed in topic and handler values are null
     *
     * @param configuration The configuration that define the consumer configuration
     * @param handler The actual handler that will operate on the topics' messages
     */
    public void subscribePushConsumer(NatsConsumerConfiguration configuration, MessageHandler handler) {
        if (CollectionUtils.isEmpty(configuration.consumerConfiguration().filterSubjects())) {
            throw new NatsException("Please provide at least one non-null NATS filter subject");
        }

        if (null == configuration.natsPushSubscriberConfiguration()) {
            throw new NatsException("Cannot register push consumer with null configuration options");
        }

        if (null == handler) {
            throw new NatsException("Please provide a non-null message handler instance");
        }

        try {
            var dispatcher = connection.createDispatcher();
            jetStream.subscribe(null,
                    configuration.natsPushSubscriberConfiguration().deliverGroup(),
                    dispatcher,
                    handler,
                    true,
                    createPushSubscribeOptions(configuration));
            LOGGER.info("Subscribed NATS push consumer {} to subject(s) {}", getConsumerName(configuration), configuration.consumerConfiguration().filterSubjects());
            dispatchers.put(configuration.consumerConfiguration().durable(), dispatcher);
        } catch (JetStreamApiException | IOException e) {
            throw new NatsException(String.format("Subscribing push consumer with name %s to NATS failed", getConsumerName(configuration)), e);
        }
    }

    /**
     * Closes all registered dispatchers.
     */
    protected void closeDispatchers() {
        dispatchers.forEach((name, dispatcher) -> {
            LOGGER.info("Closing dispatcher for consumer with name {}", name);
            connection.closeDispatcher(dispatcher);
        });
        dispatchers.clear();
    }

    /**
     * Creates the options for connecting to the NATS server.
     *
     * @return The options for connecting to the NATS server
     */
    protected Options createOptions() {
        var optionsBuilder = new Options.Builder()
                .errorListener(errorListener)
                .connectionListener(connectionListener)
                .maxReconnects(configuration.getMaxReconnects());

        if (configuration.useDispatcherWithExecutor()) {
            optionsBuilder.useDispatcherWithExecutor();
            optionsBuilder.executor(natsExecutor());
        }

        if (configuration.traceConnection()) {
            optionsBuilder.traceConnection();
        }
        configuration.getServerUrls().forEach(optionsBuilder::server);
        return optionsBuilder.build();
    }

    /**
     * Gets the consumer name from the configuration.
     *
     * @param configuration The configuration that will be used to create the push subscribe options
     * @return The consumer name
     */
    protected String getConsumerName(NatsConsumerConfiguration configuration) {
        String name;
        if (StringUtils.isNotEmpty(configuration.consumerConfiguration().durable())) {
            name = configuration.consumerConfiguration().durable();
        } else {
            name = configuration.natsPushSubscriberConfiguration().name();
        }
        return name;
    }

    /**
     * Creates the push subscribe options from the configuration.
     *
     * @param configuration The configuration that will be used to create the push subscribe options
     * @return The push subscribe options
     */
    protected PushSubscribeOptions createPushSubscribeOptions(NatsConsumerConfiguration configuration) {
        return PushSubscribeOptions.builder()
                .name(configuration.natsPushSubscriberConfiguration().name())
                .deliverGroup(configuration.natsPushSubscriberConfiguration().deliverGroup())
                .deliverSubject(configuration.natsPushSubscriberConfiguration().deliverSubject())
                .configuration(createConsumerConfiguration(configuration))
                .build();
    }

    /**
     * Creates the consumer configuration from the provided configuration.
     *
     * @param configuration The configuration that will be used to create the consumer configuration
     * @return The consumer configuration
     */
    protected ConsumerConfiguration createConsumerConfiguration(NatsConsumerConfiguration configuration) {
        return ConsumerConfiguration.builder()
                .durable(configuration.consumerConfiguration().durable())
                .filterSubjects(configuration.consumerConfiguration().filterSubjects())
                .ackPolicy(configuration.consumerConfiguration().ackPolicy())
                .ackWait(configuration.consumerConfiguration().ackAwait())
                .deliverPolicy(configuration.consumerConfiguration().deliverPolicy())
                .startSequence(configuration.consumerConfiguration().optStartSeq())
                .startTime(configuration.consumerConfiguration().optStartTime())
                .description(configuration.consumerConfiguration().description())
                .inactiveThreshold(configuration.consumerConfiguration().inactiveThreshold())
                .maxAckPending(configuration.consumerConfiguration().maxAckPending())
                .maxDeliver(configuration.consumerConfiguration().maxDeliver())
                .replayPolicy(configuration.consumerConfiguration().replayPolicy())
                .numReplicas(configuration.consumerConfiguration().replicas())
                .memStorage(configuration.consumerConfiguration().memoryStorage())
                .sampleFrequency(configuration.consumerConfiguration().sampleFrequency())
                .build();
    }

    private ExecutorService natsExecutor() {
        return Executors.newFixedThreadPool(configuration.getExecutorPoolSize(), Thread.ofVirtual()
                .name(configuration.getExecutorNamingPrefix(), 0)
                .factory());
    }

}
