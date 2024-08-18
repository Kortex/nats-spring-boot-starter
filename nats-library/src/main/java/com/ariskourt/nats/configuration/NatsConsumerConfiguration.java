package com.ariskourt.nats.configuration;

import com.ariskourt.nats.exception.NatsException;

import java.util.function.Consumer;

public record NatsConsumerConfiguration(ConsumerConfiguration consumerConfiguration,
                                        NatsPushSubscriberConfiguration natsPushSubscriberConfiguration) {

    public static NatsConsumerPropertiesBuilder builder() {
        return new NatsConsumerPropertiesBuilder();
    }


    public static class NatsConsumerPropertiesBuilder {

        private ConsumerConfiguration consumerConfiguration;
        public ConsumerConfiguration getConsumerConfiguration() {
            return consumerConfiguration;
        }

        public void setConsumerConfiguration(ConsumerConfiguration consumerConfiguration) {
            this.consumerConfiguration = consumerConfiguration;
        }

        private NatsPushSubscriberConfiguration pushSubscriberConfiguration;
        public NatsPushSubscriberConfiguration getPushSubscriberConfiguration() {
            return pushSubscriberConfiguration;
        }

        public void setPushSubscriberConfiguration(NatsPushSubscriberConfiguration pushSubscriberConfiguration) {
            this.pushSubscriberConfiguration = pushSubscriberConfiguration;
        }

        public NatsConsumerPropertiesBuilder with(Consumer<NatsConsumerPropertiesBuilder> builder) {
            builder.accept(this);
            return this;
        }

        public NatsConsumerConfiguration build() {
            validate();
            return new NatsConsumerConfiguration(consumerConfiguration, pushSubscriberConfiguration);
        }

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
