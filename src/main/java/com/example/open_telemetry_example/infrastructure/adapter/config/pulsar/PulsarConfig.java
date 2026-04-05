package com.example.open_telemetry_example.infrastructure.adapter.config.pulsar;

import com.example.open_telemetry_example.infrastructure.adapter.output.event.PulsarMessageType;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class PulsarConfig {
    @Value("${pulsar.service-url:pulsar://localhost:6650}")
    private String url;

    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        return PulsarClient.builder()
                .serviceUrl(url)
                .operationTimeout(30, TimeUnit.SECONDS)
                .connectionTimeout(30, TimeUnit.SECONDS)
                .keepAliveInterval(30, TimeUnit.SECONDS)
                .maxLookupRequests(50000)
                .lookupTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Bean("userRegistrationProducer")
    public Producer<byte[]> userRegistrationProducer(PulsarClient pulsarClient) throws PulsarClientException {
        String topic = MessageUtils
                .getTopicFromMessageType(PulsarMessageType.USER_REGISTRATION_NOTIFICATION);
        return pulsarClient.newProducer(Schema.BYTES).topic(topic).create();
    }
}
