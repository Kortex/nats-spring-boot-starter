package com.ariskourt.nats.starter.exception;

/**
 * Exception thrown when there is a configuration error in the NATS setup.
 */
public class NatsConfigurationException extends RuntimeException {

    /**
     * Constructs a new NatsConfigurationException with the specified detail message.
     *
     * @param message the detail message
     */
    public NatsConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new NatsConfigurationException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public NatsConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
