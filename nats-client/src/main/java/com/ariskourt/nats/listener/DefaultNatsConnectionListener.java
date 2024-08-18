package com.ariskourt.nats.listener;

import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the ConnectionListener interface for handling NATS connection events.
 */
public class DefaultNatsConnectionListener implements ConnectionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNatsConnectionListener.class);

    /**
     * Handles connection events from the NATS server.
     *
     * @param conn the NATS connection
     * @param type the type of connection event
     */
    @Override
    public void connectionEvent(Connection conn, Events type) {
        switch (type) {
            case CONNECTED    -> LOGGER.info("Connected to NATS server {}. Server details {}", conn.getConnectedUrl(), conn.getServerInfo());
            case DISCONNECTED -> LOGGER.warn("Disconnected from NATS server");
            case RECONNECTED  -> LOGGER.info("Reconnected to NATS server {}. Server details {}", conn.getConnectedUrl(), conn.getServerInfo());
            default           -> LOGGER.info("Got NATS event of type {}", type);
        }
    }

}
