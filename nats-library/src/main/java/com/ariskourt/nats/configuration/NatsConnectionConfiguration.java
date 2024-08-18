package com.ariskourt.nats.configuration;

import com.ariskourt.nats.exception.NatsException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 * Configuration class for NATS connection settings.
 */
public class NatsConnectionConfiguration extends Properties {

    /**
     * Retrieves the list of NATS server URLs from the configuration.
     *
     * @return a list of NATS server URLs
     * @throws NatsException if no NATS server URLs are provided
     */
    public List<String> getServerUrls() {
        var urls = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_URLS);
        if (StringUtils.isNotEmpty(urls)) {
            return List.of(urls.split(","));
        }
        throw new NatsException("No NATS server URLs have been provided!");
    }

    /**
     * Retrieves the maximum number of reconnect attempts from the configuration.
     *
     * @return the maximum number of reconnect attempts
     */
    public int getMaxReconnects() {
        var maxReconnects = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_MAX_RECONNECTS, "-1");
        return Integer.parseInt(maxReconnects);
    }

    /**
     * Checks if connection tracing is enabled in the configuration.
     *
     * @return true if connection tracing is enabled, false otherwise
     */
    public boolean traceConnection() {
        var traceConnection = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_TRACE_CONNECTION);
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
        var drainAwaitSeconds = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_DRAIN_AWAIT_SECONDS, "10");
        return Integer.parseInt(drainAwaitSeconds);
    }

    /**
     * Checks if the dispatcher should use an executor service.
     *
     * @return true if the dispatcher should use an executor service, false otherwise
     */
    public boolean useDispatcherWithExecutor() {
        var useDispatcherWithExecutor = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_USE_DISPATCHER_WITH_EXECUTOR);
        if (StringUtils.isNotEmpty(useDispatcherWithExecutor)) {
            return Boolean.parseBoolean(useDispatcherWithExecutor);
        }
        return false;
    }

    /**
     * Retrieves the executor service from the configuration.
     *
     * @return the executor service
     */
    public ExecutorService getExecutorService() {
        return (ExecutorService) get(NatsConnectionConfigurationParameters.NATS_CONNECTION_EXECUTOR_SERVICE);
    }

}
