package com.ariskourt.nats.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nats")
public record NatsProperties(String urls,
                             Integer maxReconnects,
                             Boolean traceConnection,
                             Integer drainAwaitSeconds,
                             Boolean useDispatcherWithExecutor) {
}
