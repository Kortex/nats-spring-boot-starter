package com.ariskourt.nats.configuration;

import java.util.function.Consumer;

/**
 * Configuration class for NATS push subscriber settings.
 *
 * @param name The name of the push subscriber.
 * @param deliverSubject The subject to which messages are delivered.
 * @param deliverGroup The group to which messages are delivered.
 */
public record NatsPushSubscriberConfiguration(String name, String deliverSubject, String deliverGroup) {

    /**
     * Creates a new builder for NatsPushSubscriberConfiguration.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing NatsPushSubscriberConfiguration instances.
     */
    public static class Builder {

        private String name;

        /**
         * Gets the name of the push subscriber.
         *
         * @return the name of the push subscriber
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the push subscriber.
         *
         * @param name the name of the push subscriber
         */
        public void setName(String name) {
            this.name = name;
        }

        private String deliverSubject;

        /**
         * Gets the subject to which messages are delivered.
         *
         * @return the subject to which messages are delivered
         */
        public String getDeliverSubject() {
            return deliverSubject;
        }

        /**
         * Sets the subject to which messages are delivered.
         *
         * @param deliverSubject the subject to which messages are delivered
         */
        public void setDeliverSubject(String deliverSubject) {
            this.deliverSubject = deliverSubject;
        }

        private String deliverGroup;

        /**
         * Gets the group to which messages are delivered.
         *
         * @return the group to which messages are delivered
         */
        public String getDeliverGroup() {
            return deliverGroup;
        }

        /**
         * Sets the group to which messages are delivered.
         *
         * @param deliverGroup the group to which messages are delivered
         */
        public void setDeliverGroup(String deliverGroup) {
            this.deliverGroup = deliverGroup;
        }

        /**
         * Applies the given consumer to this builder.
         *
         * @param builder the consumer to apply
         * @return this builder
         */
        public Builder with(Consumer<Builder> builder) {
            builder.accept(this);
            return this;
        }

        /**
         * Builds a new NatsPushSubscriberConfiguration instance.
         *
         * @return a new NatsPushSubscriberConfiguration instance
         */
        public NatsPushSubscriberConfiguration build() {
            return new NatsPushSubscriberConfiguration(name, deliverSubject, deliverGroup);
        }

    }

}
