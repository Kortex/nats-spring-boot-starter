package com.ariskourt.nats.starter.exception;

public class NatsConfigurationException extends RuntimeException {

    public NatsConfigurationException(String message) {
        super(message);
    }

    public NatsConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
