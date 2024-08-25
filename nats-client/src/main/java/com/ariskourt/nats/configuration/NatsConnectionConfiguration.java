package com.ariskourt.nats.configuration;

import com.ariskourt.nats.exception.NatsException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * Configuration class for NATS connection settings.
 */
public class NatsConnectionConfiguration extends Properties {

    /**
     * Default NATS server URL.
     */
    public static final String DEFAULT_NATS_URL               = "localhost:4222";

    /**
     * Default maximum number of reconnect attempts.
     */
    public static final String DEFAULT_MAX_RECONNECTS         = "-1";

    /**
     * Default number of seconds to wait for draining the connection before closing.
     */
    public static final String DEFAULT_DRAIN_AWAIT_SECONDS    = "10";

    /**
     * Default size of the executor pool.
     */
    public static final String DEFAULT_EXECUTOR_POOL_SIZE     = "10";

    /**
     * Default naming prefix for the executor.
     */
    public static final String DEFAULT_EXECUTOR_NAMING_PREFIX = "nats-";

    /**
     * Checks if NATS is enabled in the configuration.
     *
     * @return true if NATS is enabled, false otherwise
     */
    public boolean enabled() {
        var enabled = getProperty(NatsConnectionConfigurationParameters.NATS_ENABLED);
        if (StringUtils.isNotEmpty(enabled)) {
            return Boolean.parseBoolean(enabled);
        }
        return false;
    }

    /**
     * Retrieves the list of NATS server URLs from the configuration.
     *
     * @return a list of NATS server URLs
     * @throws NatsException if no NATS server URLs are provided
     */
    public List<String> getServerUrls() {
        var urls = getProperty(NatsConnectionConfigurationParameters.NATS_URLS);
        if (StringUtils.isNotEmpty(urls)) {
            return List.of(urls.split(","));
        }
        return List.of(DEFAULT_NATS_URL);
    }

    /**
     * Retrieves the maximum number of reconnect attempts from the configuration.
     *
     * @return the maximum number of reconnect attempts
     */
    public int getMaxReconnects() {
        var maxReconnects = getProperty(NatsConnectionConfigurationParameters.NATS_MAX_RECONNECTS, DEFAULT_MAX_RECONNECTS);
        return Integer.parseInt(maxReconnects);
    }

    /**
     * Checks if connection tracing is enabled in the configuration.
     *
     * @return true if connection tracing is enabled, false otherwise
     */
    public boolean traceConnection() {
        var traceConnection = getProperty(NatsConnectionConfigurationParameters.NATS_TRACE_CONNECTION);
        if (StringUtils.isNotEmpty(traceConnection)) {
            return Boolean.parseBoolean(traceConnection);
        }
        return false;
    }

    /**
     * Retrieves the number of seconds to wait for draining the connection before closing.
     *
     * @return the number of seconds to wait for draining the connection
     */
    public int getDrainAwaitSeconds() {
        var drainAwaitSeconds = getProperty(NatsConnectionConfigurationParameters.NATS_DRAIN_AWAIT_SECONDS, DEFAULT_DRAIN_AWAIT_SECONDS);
        return Integer.parseInt(drainAwaitSeconds);
    }

    /**
     * Checks if the dispatcher should use an executor service.
     *
     * @return true if the dispatcher should use an executor service, false otherwise
     */
    public boolean useDispatcherWithExecutor() {
        var useDispatcherWithExecutor = getProperty(NatsConnectionConfigurationParameters.NATS_USE_DISPATCHER_WITH_EXECUTOR);
        if (StringUtils.isNotEmpty(useDispatcherWithExecutor)) {
            return Boolean.parseBoolean(useDispatcherWithExecutor);
        }
        return false;
    }

    /**
     * Retrieves the size of the executor pool from the configuration.
     *
     * @return the size of the executor pool
     */
    public int getExecutorPoolSize() {
        var poolSize = getProperty(NatsConnectionConfigurationParameters.NATS_EXECUTOR_POOL_SIZE, DEFAULT_EXECUTOR_POOL_SIZE);
        return Integer.parseInt(poolSize);
    }

    /**
     * Retrieves the naming prefix for the executor from the configuration.
     *
     * @return the naming prefix for the executor
     */
    public String getExecutorNamingPrefix() {
        return getProperty(NatsConnectionConfigurationParameters.NATS_EXECUTOR_NAMING_PREFIX, DEFAULT_EXECUTOR_NAMING_PREFIX);
    }

}