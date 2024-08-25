package com.ariskourt.nats.configuration;

/**
 * This class holds the configuration parameter keys for NATS connection settings.
 */
public final class NatsConnectionConfigurationParameters {

    /**
     * Key for enabling or disabling NATS.
     */
    public static final String NATS_ENABLED = "nats.enabled";

    /**
     * Key for the NATS connection URLs.
     */
    public static final String NATS_URLS = "nats.urls";

    /**
     * Key for the maximum number of reconnect attempts.
     */
    public static final String NATS_MAX_RECONNECTS = "nats.maxReconnects";

    /**
     * Key for enabling or disabling connection tracing.
     */
    public static final String NATS_TRACE_CONNECTION = "nats.traceConnection";

    /**
     * Key for the number of seconds to wait for draining the connection before closing.
     */
    public static final String NATS_DRAIN_AWAIT_SECONDS = "nats.drainAwaitSeconds";

    /**
     * Key for enabling or disabling the use of dispatcher with executor.
     */
    public static final String NATS_USE_DISPATCHER_WITH_EXECUTOR = "nats.useDispatcherWithExecutor";

    /**
     * Key for the size of the executor pool.
     */
    public static final String NATS_EXECUTOR_POOL_SIZE = "nats.executor.poolSize";

    /**
     * Key for the naming prefix of the executor.
     */
    public static final String NATS_EXECUTOR_NAMING_PREFIX = "nats.executor.namingPrefix";

}