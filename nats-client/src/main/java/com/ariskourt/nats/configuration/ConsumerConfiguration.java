package com.ariskourt.nats.configuration;

import io.nats.client.api.AckPolicy;
import io.nats.client.api.DeliverPolicy;
import io.nats.client.api.ReplayPolicy;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

/**
 * Configuration class for NATS consumer settings.
 *
 * @param durable The durable name for the consumer.
 * @param filterSubjects The subjects to filter messages.
 * @param ackPolicy The acknowledgment policy.
 * @param ackAwait The duration to wait for an acknowledgment.
 * @param deliverPolicy The delivery policy.
 * @param optStartSeq The optional start sequence.
 * @param optStartTime The optional start time.
 * @param description The description of the consumer.
 * @param inactiveThreshold The threshold for inactivity.
 * @param maxAckPending The maximum acknowledgments pending.
 * @param maxDeliver The maximum number of deliveries.
 * @param replayPolicy The replay policy.
 * @param replicas The number of replicas.
 * @param memoryStorage Whether to use memory storage.
 * @param sampleFrequency The sample frequency.
 */
public record ConsumerConfiguration(String durable, List<String> filterSubjects, AckPolicy ackPolicy,
                                    Duration ackAwait, DeliverPolicy deliverPolicy, Long optStartSeq,
                                    ZonedDateTime optStartTime, String description, Duration inactiveThreshold,
                                    Long maxAckPending, Long maxDeliver, ReplayPolicy replayPolicy,
                                    Integer replicas, Boolean memoryStorage, String sampleFrequency) {

    /**
     * Creates a new builder for ConsumerConfiguration.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing ConsumerConfiguration instances.
     */
    public static class Builder {

        private String durable;
        private List<String> filterSubjects;
        private AckPolicy ackPolicy = AckPolicy.Explicit;
        private Duration ackAwait;
        private DeliverPolicy deliverPolicy = DeliverPolicy.All;
        private Long optStartSeq;
        private ZonedDateTime optStartTime;
        private String description;
        private Duration inactiveThreshold;
        private Long maxAckPending;
        private Long maxDeliver;
        private ReplayPolicy replayPolicy = ReplayPolicy.Instant;
        private Integer replicas;
        private Boolean memoryStorage;
        private String sampleFrequency;

        /**
         * Gets the durable name.
         *
         * @return the durable name
         */
        public String getDurable() {
            return durable;
        }

        /**
         * Sets the durable name.
         *
         * @param durable the durable name
         */
        public void setDurable(String durable) {
            this.durable = durable;
        }

        /**
         * Gets the filter subjects.
         *
         * @return the filter subjects
         */
        public List<String> getFilterSubjects() {
            return filterSubjects;
        }

        /**
         * Sets the filter subjects.
         *
         * @param filterSubjects the filter subjects
         */
        public void setFilterSubjects(List<String> filterSubjects) {
            this.filterSubjects = filterSubjects;
        }

        /**
         * Gets the acknowledgment policy.
         *
         * @return the acknowledgment policy
         */
        public AckPolicy getAckPolicy() {
            return ackPolicy;
        }

        /**
         * Sets the acknowledgment policy.
         *
         * @param ackPolicy the acknowledgment policy
         */
        public void setAckPolicy(AckPolicy ackPolicy) {
            this.ackPolicy = ackPolicy;
        }

        /**
         * Gets the acknowledgment await duration.
         *
         * @return the acknowledgment await duration
         */
        public Duration getAckAwait() {
            return ackAwait;
        }

        /**
         * Sets the acknowledgment await duration.
         *
         * @param ackAwait the acknowledgment await duration
         */
        public void setAckAwait(Duration ackAwait) {
            this.ackAwait = ackAwait;
        }

        /**
         * Gets the delivery policy.
         *
         * @return the delivery policy
         */
        public DeliverPolicy getDeliverPolicy() {
            return deliverPolicy;
        }

        /**
         * Sets the delivery policy.
         *
         * @param deliverPolicy the delivery policy
         */
        public void setDeliverPolicy(DeliverPolicy deliverPolicy) {
            this.deliverPolicy = deliverPolicy;
        }

        /**
         * Gets the optional start sequence.
         *
         * @return the optional start sequence
         */
        public Long getOptStartSeq() {
            return optStartSeq;
        }

        /**
         * Sets the optional start sequence.
         *
         * @param optStartSeq the optional start sequence
         */
        public void setOptStartSeq(Long optStartSeq) {
            this.optStartSeq = optStartSeq;
        }

        /**
         * Gets the optional start time.
         *
         * @return the optional start time
         */
        public ZonedDateTime getOptStartTime() {
            return optStartTime;
        }

        /**
         * Sets the optional start time.
         *
         * @param optStartTime the optional start time
         */
        public void setOptStartTime(ZonedDateTime optStartTime) {
            this.optStartTime = optStartTime;
        }

        /**
         * Gets the description.
         *
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the description.
         *
         * @param description the description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Gets the inactive threshold.
         *
         * @return the inactive threshold
         */
        public Duration getInactiveThreshold() {
            return inactiveThreshold;
        }

        /**
         * Sets the inactive threshold.
         *
         * @param inactiveThreshold the inactive threshold
         */
        public void setInactiveThreshold(Duration inactiveThreshold) {
            this.inactiveThreshold = inactiveThreshold;
        }

        /**
         * Gets the maximum acknowledgments pending.
         *
         * @return the maximum acknowledgments pending
         */
        public Long getMaxAckPending() {
            return maxAckPending;
        }

        /**
         * Sets the maximum acknowledgments pending.
         *
         * @param maxAckPending the maximum acknowledgments pending
         */
        public void setMaxAckPending(Long maxAckPending) {
            this.maxAckPending = maxAckPending;
        }

        /**
         * Gets the maximum number of deliveries.
         *
         * @return the maximum number of deliveries
         */
        public Long getMaxDeliver() {
            return maxDeliver;
        }

        /**
         * Sets the maximum number of deliveries.
         *
         * @param maxDeliver the maximum number of deliveries
         */
        public void setMaxDeliver(Long maxDeliver) {
            this.maxDeliver = maxDeliver;
        }

        /**
         * Gets the replay policy.
         *
         * @return the replay policy
         */
        public ReplayPolicy getReplayPolicy() {
            return replayPolicy;
        }

        /**
         * Sets the replay policy.
         *
         * @param replayPolicy the replay policy
         */
        public void setReplayPolicy(ReplayPolicy replayPolicy) {
            this.replayPolicy = replayPolicy;
        }

        /**
         * Gets the number of replicas.
         *
         * @return the number of replicas
         */
        public Integer getReplicas() {
            return replicas;
        }

        /**
         * Sets the number of replicas.
         *
         * @param replicas the number of replicas
         */
        public void setReplicas(Integer replicas) {
            this.replicas = replicas;
        }

        /**
         * Gets whether to use memory storage.
         *
         * @return whether to use memory storage
         */
        public Boolean getMemoryStorage() {
            return memoryStorage;
        }

        /**
         * Sets whether to use memory storage.
         *
         * @param memoryStorage whether to use memory storage
         */
        public void setMemoryStorage(Boolean memoryStorage) {
            this.memoryStorage = memoryStorage;
        }

        /**
         * Gets the sample frequency.
         *
         * @return the sample frequency
         */
        public String getSampleFrequency() {
            return sampleFrequency;
        }

        /**
         * Sets the sample frequency.
         *
         * @param sampleFrequency the sample frequency
         */
        public void setSampleFrequency(String sampleFrequency) {
            this.sampleFrequency = sampleFrequency;
        }

        /**
         * Applies the given consumer to this builder.
         *
         * @param bf the consumer to apply
         * @return this builder
         */
        public Builder with(Consumer<Builder> bf) {
            bf.accept(this);
            return this;
        }

        /**
         * Builds a new ConsumerConfiguration instance.
         *
         * @return a new ConsumerConfiguration instance
         */
        public ConsumerConfiguration build() {
            return new ConsumerConfiguration(durable, filterSubjects, ackPolicy,
                    ackAwait, deliverPolicy, optStartSeq,
                    optStartTime, description, inactiveThreshold,
                    maxAckPending, maxDeliver, replayPolicy,
                    replicas, memoryStorage, sampleFrequency);
        }

    }

}
