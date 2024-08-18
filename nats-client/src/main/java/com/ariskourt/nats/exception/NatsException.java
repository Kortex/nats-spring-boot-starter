package com.ariskourt.nats.exception;

import java.util.Optional;

/**
 * Custom exception class for handling NATS-related errors.
 */
public class NatsException extends RuntimeException {

    /**
     * Default exception message used when no specific message is provided.
     */
    static final String DEFAULT_EXCEPTION_MESSAGE = "An generic NATS error has occurred!";

    /**
     * Constructs a new NatsException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public NatsException(Throwable cause) {
        super(cause.getMessage());
    }

    /**
     * Constructs a new NatsException with the specified detail message.
     *
     * @param message the detail message
     */
    public NatsException(String message) {
        super(message);
    }

    /**
     * Constructs a new NatsException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public NatsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns the detail message string of this exception.
     * If the message is null, returns a default exception message.
     *
     * @return the detail message string of this exception
     */
    @Override
    public String getMessage() {
        return Optional.ofNullable(super.getMessage()).orElse(DEFAULT_EXCEPTION_MESSAGE);
    }

}
