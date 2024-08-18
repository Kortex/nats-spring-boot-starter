package com.ariskourt.nats.configuration;

import java.util.function.Consumer;

public record NatsPushSubscriberConfiguration(String name, String deliverSubject, String deliverGroup) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String deliverSubject;
        public String getDeliverSubject() {
            return deliverSubject;
        }

        public void setDeliverSubject(String deliverSubject) {
            this.deliverSubject = deliverSubject;
        }

        private String deliverGroup;
        public String getDeliverGroup() {
            return deliverGroup;
        }

        public void setDeliverGroup(String deliverGroup) {
            this.deliverGroup = deliverGroup;
        }

        public Builder with(Consumer<Builder> builder) {
            builder.accept(this);
            return this;
        }

        public NatsPushSubscriberConfiguration build() {
            return new NatsPushSubscriberConfiguration(name, deliverSubject, deliverGroup);
        }

    }

}
