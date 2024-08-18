package com.ariskourt.nats.exception;

import java.util.Optional;

public class NatsException extends RuntimeException {

    static final String DEFAULT_EXCEPTION_MESSAGE = "An generic NATS error has occurred!";

    public NatsException(Throwable cause) {
        super(cause.getMessage());
    }

    public NatsException(String message) {
        super(message);
    }

    public NatsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return Optional.ofNullable(super.getMessage()).orElse(DEFAULT_EXCEPTION_MESSAGE);
    }

}
