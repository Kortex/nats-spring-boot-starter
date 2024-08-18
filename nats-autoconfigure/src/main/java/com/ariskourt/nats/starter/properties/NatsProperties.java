package com.ariskourt.nats.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for NATS.
 * <p>
 * This class holds the configuration properties for connecting to a NATS server.
 * The properties are prefixed with "nats" in the application configuration.
 * </p>
 *
 * @param urls The URLs of the NATS servers.
 * @param maxReconnects The maximum number of reconnection attempts.
 * @param traceConnection Whether to enable connection tracing.
 * @param drainAwaitSeconds The number of seconds to wait for draining connections.
 * @param useDispatcherWithExecutor Whether to use a dispatcher with an executor service.
 */
@ConfigurationProperties(prefix = "nats")
public record NatsProperties(String urls,
                             Integer maxReconnects,
                             Boolean traceConnection,
                             Integer drainAwaitSeconds,
                             Boolean useDispatcherWithExecutor) {
}
