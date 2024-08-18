package com.ariskourt.nats.configuration;

import com.ariskourt.nats.exception.NatsException;

import java.util.function.Consumer;

/**
 * Configuration class for NATS consumer settings.
 *
 * @param consumerConfiguration The consumer configuration settings.
 * @param natsPushSubscriberConfiguration The push subscriber configuration settings.
 */
public record NatsConsumerConfiguration(ConsumerConfiguration consumerConfiguration,
                                        NatsPushSubscriberConfiguration natsPushSubscriberConfiguration) {

    /**
     * Creates a new builder for NatsConsumerConfiguration.
     *
     * @return a new NatsConsumerPropertiesBuilder instance
     */
    public static NatsConsumerPropertiesBuilder builder() {
        return new NatsConsumerPropertiesBuilder();
    }

    /**
     * Builder class for constructing NatsConsumerConfiguration instances.
     */
    public static class NatsConsumerPropertiesBuilder {

        private ConsumerConfiguration consumerConfiguration;

        /**
         * Gets the consumer configuration.
         *
         * @return the consumer configuration
         */
        public ConsumerConfiguration getConsumerConfiguration() {
            return consumerConfiguration;
        }

        /**
         * Sets the consumer configuration.
         *
         * @param consumerConfiguration the consumer configuration
         */
        public void setConsumerConfiguration(ConsumerConfiguration consumerConfiguration) {
            this.consumerConfiguration = consumerConfiguration;
        }

        private NatsPushSubscriberConfiguration pushSubscriberConfiguration;

        /**
         * Gets the push subscriber configuration.
         *
         * @return the push subscriber configuration
         */
        public NatsPushSubscriberConfiguration getPushSubscriberConfiguration() {
            return pushSubscriberConfiguration;
        }

        /**
         * Sets the push subscriber configuration.
         *
         * @param pushSubscriberConfiguration the push subscriber configuration
         */
        public void setPushSubscriberConfiguration(NatsPushSubscriberConfiguration pushSubscriberConfiguration) {
            this.pushSubscriberConfiguration = pushSubscriberConfiguration;
        }

        /**
         * Applies the given consumer to this builder.
         *
         * @param builder the consumer to apply
         * @return this builder
         */
        public NatsConsumerPropertiesBuilder with(Consumer<NatsConsumerPropertiesBuilder> builder) {
            builder.accept(this);
            return this;
        }

        /**
         * Builds a new NatsConsumerConfiguration instance.
         *
         * @return a new NatsConsumerConfiguration instance
         */
        public NatsConsumerConfiguration build() {
            validate();
            return new NatsConsumerConfiguration(consumerConfiguration, pushSubscriberConfiguration);
        }

        /**
         * Validates the builder's state before building.
         *
         * @throws NatsException if the consumer configuration or push subscriber configuration is null
         */
        private void validate() {
            if (consumerConfiguration == null) {
                throw new NatsException("Cannot create a NATS consumer without a consumer configuration!");
            }
            if (pushSubscriberConfiguration == null) {
                throw new NatsException("Cannot create a NATS consumer without a push subscriber configuration!");
            }
        }

    }

}
