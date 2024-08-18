package com.ariskourt.nats.listener;

import io.nats.client.Connection;
import io.nats.client.ErrorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the ErrorListener interface for handling NATS error events.
 */
public class DefaultNatsErrorListener implements ErrorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNatsErrorListener.class);

    /**
     * Handles error events from the NATS server.
     *
     * @param conn the NATS connection
     * @param error the error message
     */
    @Override
    public void errorOccurred(Connection conn, String error) {
        LOGGER.error("A NATS server error has occurred! Error message was: {}", error);
    }

    /**
     * Handles exceptions that occur during NATS operations.
     *
     * @param conn the NATS connection
     * @param exp the exception that occurred
     */
    @Override
    public void exceptionOccurred(Connection conn, Exception exp) {
        LOGGER.error("An exception has occurred while performing a NATS operation. Error message was: {}", exp.getMessage(), exp);
    }

}
