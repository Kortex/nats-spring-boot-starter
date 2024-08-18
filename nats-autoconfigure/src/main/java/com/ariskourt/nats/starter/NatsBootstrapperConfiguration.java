package com.ariskourt.nats.starter;

import com.ariskourt.nats.Nats;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@AutoConfiguration
public class NatsBootstrapperConfiguration {

    private final Nats nats;
    public NatsBootstrapperConfiguration(Nats nats) {
        this.nats = nats;
    }

    @PostConstruct
    public void connect() {
        nats.connect();
    }

    @PreDestroy
    public void disconnect() {
        nats.disconnect();
    }

}
