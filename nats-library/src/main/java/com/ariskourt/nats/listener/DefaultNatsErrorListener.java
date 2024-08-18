package com.ariskourt.nats.listener;

import io.nats.client.Connection;
import io.nats.client.ErrorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultNatsErrorListener implements ErrorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNatsErrorListener.class);

    @Override
    public void errorOccurred(Connection conn, String error) {
        LOGGER.error("A NATS server error has occurred! Error message was: {}", error);
    }

    @Override
    public void exceptionOccurred(Connection conn, Exception exp) {
        LOGGER.error("An exception has occurred while performing a NATS operation. Error message was: {}", exp.getMessage(), exp);
    }

}
