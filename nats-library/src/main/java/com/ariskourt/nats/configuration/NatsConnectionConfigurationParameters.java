package com.ariskourt.nats.configuration;

/**
 * This class holds the configuration parameter keys for NATS connection settings.
 */
public final class NatsConnectionConfigurationParameters {

    /**
     * Key for the NATS connection URLs.
     */
    public static final String NATS_CONNECTION_URLS = "nats.connection.urls";

    /**
     * Key for the maximum number of reconnect attempts.
     */
    public static final String NATS_CONNECTION_MAX_RECONNECTS = "nats.connection.maxReconnects";

    /**
     * Key for enabling or disabling connection tracing.
     */
    public static final String NATS_CONNECTION_TRACE_CONNECTION = "nats.connection.traceConnection";

    /**
     * Key for the number of seconds to wait for draining the connection before closing.
     */
    public static final String NATS_CONNECTION_DRAIN_AWAIT_SECONDS = "nats.connection.drainAwaitSeconds";

    /**
     * Key for the executor service used by the connection.
     */
    public static final String NATS_CONNECTION_EXECUTOR_SERVICE = "nats.connection.executorService";

    /**
     * Key for enabling or disabling the use of dispatcher with executor.
     */
    public static final String NATS_CONNECTION_USE_DISPATCHER_WITH_EXECUTOR = "nats.connection.useDispatcherWithExecutor";

}
