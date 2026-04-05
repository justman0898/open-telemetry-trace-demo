package com.example.open_telemetry_example.infrastructure.adapter.output.event;

import com.example.open_telemetry_example.application.ports.output.EventProducerOutputPort;
import io.micrometer.tracing.annotation.NewSpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventProducer implements EventProducerOutputPort {
    private final ObjectMapper objectMapper;
    private final PulsarClient pulsarClient;
    private final Producer<byte[]> userRegistrationProducer;


    @Override
    public <T> void produceNotificationMessage(T body, String topic, Map<String, String> traceHeaders) throws PulsarClientException {
        String serializedBody = objectMapper.writeValueAsString(body);
        byte[] serializedBytesBody = serializedBody.getBytes(StandardCharsets.UTF_8);

        this.userRegistrationProducer.newMessage()
                .value(serializedBytesBody)
                .properties(traceHeaders)
                .send();

        log.info("Produced notification message to topic: {},  body: {}", topic, serializedBody);

//        try (Producer<byte[]> producer = pulsarClient.newProducer(Schema.BYTES).topic(topic).create()) {
//            producer.newMessage()
//                    .value(serializedBytesBody)
//                    .properties(traceHeaders)
//                    .send();
//
//            log.info("Produced Notification message : {}", serializedBody);
//        }


    }
}
