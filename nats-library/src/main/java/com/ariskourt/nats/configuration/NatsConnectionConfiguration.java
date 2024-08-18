package com.ariskourt.nats.configuration;

import com.ariskourt.nats.exception.NatsException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

public class NatsConnectionConfiguration extends Properties {

    public List<String> getServerUrls() {
        var urls = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_URLS);
        if (StringUtils.isNotEmpty(urls)) {
            return List.of(urls.split(","));
        }
        throw new NatsException("No NATS server URLs have been provided!");
    }

    public int getMaxReconnects() {
        var maxReconnects = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_MAX_RECONNECTS, "-1");
        return Integer.parseInt(maxReconnects);
    }

    public boolean traceConnection() {
        var traceConnection = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_TRACE_CONNECTION);
        if (StringUtils.isNotEmpty(traceConnection)) {
            return Boolean.parseBoolean(traceConnection);
        }
        return false;
    }

    public int getDrainAwaitSeconds() {
        var drainAwaitSeconds = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_DRAIN_AWAIT_SECONDS, "10");
        return Integer.parseInt(drainAwaitSeconds);
    }

    public boolean useDispatcherWithExecutor() {
        var useDispatcherWithExecutor = getProperty(NatsConnectionConfigurationParameters.NATS_CONNECTION_USE_DISPATCHER_WITH_EXECUTOR);
        if (StringUtils.isNotEmpty(useDispatcherWithExecutor)) {
            return Boolean.parseBoolean(useDispatcherWithExecutor);
        }
        return false;
    }

    public ExecutorService getExecutorService() {
        return (ExecutorService) get(NatsConnectionConfigurationParameters.NATS_CONNECTION_EXECUTOR_SERVICE);
    }

}
