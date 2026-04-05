package com.example.open_telemetry_example.application.ports.output;


import org.apache.pulsar.client.api.PulsarClientException;

import java.util.Map;

public interface EventProducerOutputPort {
    <T> void produceNotificationMessage(T body, String topic, Map<String, String> traceHeaders) throws PulsarClientException;
}
