package com.ariskourt.nats;

import com.ariskourt.nats.configuration.NatsConnectionConfiguration;
import com.ariskourt.nats.exception.NatsException;
import com.ariskourt.nats.listener.DefaultNatsConnectionListener;
import com.ariskourt.nats.listener.DefaultNatsErrorListener;
import io.nats.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Nats {

    private static final Logger LOGGER = LoggerFactory.getLogger(Nats.class);

    protected final ErrorListener errorListener;
    protected final ConnectionListener connectionListener;
    protected final NatsConnectionConfiguration configuration;

    private Connection connection;
    public Connection getConnection() {
        return connection;
    }

    private JetStream jetStream;
    public JetStream getJetStream() {
        return jetStream;
    }

    public Nats(NatsConnectionConfiguration configuration) {
        this(configuration, null, null);
    }

    public Nats(NatsConnectionConfiguration configuration, ErrorListener errorListener, ConnectionListener connectionListener) {
        this.errorListener = errorListener == null ? new DefaultNatsErrorListener() : errorListener;
        this.connectionListener = connectionListener == null ? new DefaultNatsConnectionListener() : connectionListener;
        this.configuration = configuration;
    }


    public Nats connect() {
        try {
            LOGGER.info("Attempting to connect to NATS server using the following configuration {}", configuration);
            connection = io.nats.client.Nats.connect(createOptions());
            jetStream = connection.jetStream();
        } catch (IOException | InterruptedException e) {
            throw new NatsException("An error occurred while trying to connect to the NATS server!", e);
        }
        return this;
    }

    public Nats disconnect() {
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
            connection.close();
            LOGGER.info("NATS connection has been closed successfully");
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.error("Closing NATS connection has failed", e);
            throw new NatsException(e);
        }
        return this;
    }

    protected Options createOptions() {
        var optionsBuilder = new Options.Builder()
                .errorListener(errorListener)
                .connectionListener(connectionListener)
                .maxReconnects(configuration.getMaxReconnects());

        if (configuration.getExecutorService() != null) {
            optionsBuilder.executor(configuration.getExecutorService());
        }
        if (configuration.useDispatcherWithExecutor()) {
            optionsBuilder.useDispatcherWithExecutor();
        }
        if (configuration.traceConnection()) {
            optionsBuilder.traceConnection();
        }
        configuration.getServerUrls().forEach(optionsBuilder::server);
        return optionsBuilder.build();
    }

}
