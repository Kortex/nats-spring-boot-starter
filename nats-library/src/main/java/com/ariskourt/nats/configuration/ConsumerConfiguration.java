package com.ariskourt.nats.configuration;

import io.nats.client.api.AckPolicy;
import io.nats.client.api.DeliverPolicy;
import io.nats.client.api.ReplayPolicy;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

public record ConsumerConfiguration(String durable, List<String> filterSubjects, AckPolicy ackPolicy,
                                    Duration ackAwait, DeliverPolicy deliverPolicy, Long optStartSeq,
                                    ZonedDateTime optStartTime, String description, Duration inactiveThreshold,
                                    Long maxAckPending, Long maxDeliver, ReplayPolicy replayPolicy,
                                    Integer replicas, Boolean memoryStorage, String sampleFrequency) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String durable;
        public String getDurable() {
            return durable;
        }

        public void setDurable(String durable) {
            this.durable = durable;
        }

        private List<String> filterSubjects;
        public List<String> getFilterSubjects() {
            return filterSubjects;
        }

        public void setFilterSubjects(List<String> filterSubjects) {
            this.filterSubjects = filterSubjects;
        }

        private AckPolicy ackPolicy = AckPolicy.Explicit;
        public AckPolicy getAckPolicy() {
            return ackPolicy;
        }

        public void setAckPolicy(AckPolicy ackPolicy) {
            this.ackPolicy = ackPolicy;
        }

        private Duration ackAwait;
        public Duration getAckAwait() {
            return ackAwait;
        }

        public void setAckAwait(Duration ackAwait) {
            this.ackAwait = ackAwait;
        }

        private DeliverPolicy deliverPolicy = DeliverPolicy.All;
        public DeliverPolicy getDeliverPolicy() {
            return deliverPolicy;
        }

        public void setDeliverPolicy(DeliverPolicy deliverPolicy) {
            this.deliverPolicy = deliverPolicy;
        }

        private Long optStartSeq;
        public Long getOptStartSeq() {
            return optStartSeq;
        }

        public void setOptStartSeq(Long optStartSeq) {
            this.optStartSeq = optStartSeq;
        }

        private ZonedDateTime optStartTime;
        public ZonedDateTime getOptStartTime() {
            return optStartTime;
        }

        public void setOptStartTime(ZonedDateTime optStartTime) {
            this.optStartTime = optStartTime;
        }

        private String description;
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        private Duration inactiveThreshold;
        public Duration getInactiveThreshold() {
            return inactiveThreshold;
        }

        public void setInactiveThreshold(Duration inactiveThreshold) {
            this.inactiveThreshold = inactiveThreshold;
        }

        private Long maxAckPending;
        public Long getMaxAckPending() {
            return maxAckPending;
        }

        public void setMaxAckPending(Long maxAckPending) {
            this.maxAckPending = maxAckPending;
        }

        private Long maxDeliver;
        public Long getMaxDeliver() {
            return maxDeliver;
        }

        public void setMaxDeliver(Long maxDeliver) {
            this.maxDeliver = maxDeliver;
        }

        private ReplayPolicy replayPolicy = ReplayPolicy.Instant;
        public ReplayPolicy getReplayPolicy() {
            return replayPolicy;
        }

        public void setReplayPolicy(ReplayPolicy replayPolicy) {
            this.replayPolicy = replayPolicy;
        }

        private Integer replicas;
        public Integer getReplicas() {
            return replicas;
        }

        public void setReplicas(Integer replicas) {
            this.replicas = replicas;
        }

        private Boolean memoryStorage;
        public Boolean getMemoryStorage() {
            return memoryStorage;
        }

        public void setMemoryStorage(Boolean memoryStorage) {
            this.memoryStorage = memoryStorage;
        }

        private String sampleFrequency;
        public String getSampleFrequency() {
            return sampleFrequency;
        }

        public void setSampleFrequency(String sampleFrequency) {
            this.sampleFrequency = sampleFrequency;
        }

        public Builder with(Consumer<Builder> bf) {
            bf.accept(this);
            return this;
        }

        public ConsumerConfiguration build() {
            return new ConsumerConfiguration(durable, filterSubjects, ackPolicy,
                    ackAwait, deliverPolicy, optStartSeq,
                    optStartTime, description, inactiveThreshold,
                    maxAckPending, maxDeliver, replayPolicy,
                    replicas, memoryStorage, sampleFrequency);
        }

    }

}
