package com.ariskourt.nats.handler;

import io.nats.client.Message;
import io.nats.client.MessageHandler;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Interface representing a handler for NATS messages.
 *
 * @param <T> The type of event that this handler processes, which extends {@link NatsEvent}
 */
public interface NatsHandler<T extends NatsEvent> extends MessageHandler {

    /**
     * A method defining the consumer that corresponds to this message handler
     *
     * @return The consumer name
     */
    String durable();

    /**
     * A method defining the NATS subject(s) to which the corresponding consumer is registered at
     *
     * @return The NATS subject(s) to which this handler is registered
     */
    List<String> filterSubjects();

    /**
     * A method defining the delivery queue group name. It is used to distribute the messages between the subscribers
     * to the consumer.
     *
     * @return The delivery queue group name
     */
    default String deliverGroup() {
        return null;
    }

    /**
     * A method defining the NATS subject that the corresponding consumer delivers messages. The message handler handles
     * these messages
     *
     * @return The NATS subject name to which this handler is registered
     */
    default String deliverSubject() {
        return null;
    }

    /**
     * The class of the event that this handler is expecting
     * @return The class of the event that is handled by this handler
     */
    Class<T> eventClass();

    /**
     * Accepting a message this method will attempt to read its contents and return a string representation of it
     * @param message A NATS message that will be read
     * @return A string representation of content of the message
     */
    default String readMessage(Message message) {
        return new String(message.getData(), StandardCharsets.UTF_8);
    }

    /**
     * The number of times a message will be attempted to get delivered to the consumer before it is discarded
     *
     * @return The number of times a message will be attempted to get delivered to the consumer before it is discarded
     */
    default Long maxDeliver() {
        return 3L;
    }

    /**
     * The name assigned to this handler/consumer
     *
     * @return The name assigned to this handler/consumer
     */
    default String consumerName() {
        return null;
    }

}
