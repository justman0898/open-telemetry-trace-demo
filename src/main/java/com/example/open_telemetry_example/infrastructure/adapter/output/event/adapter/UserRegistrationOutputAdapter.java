package com.example.open_telemetry_example.infrastructure.adapter.output.event.adapter;

import com.example.open_telemetry_example.application.ports.output.UserRegistrationOutputPort;
import com.example.open_telemetry_example.domain.models.RegisterUserCommand;
import com.example.open_telemetry_example.domain.models.notification.UserRegisteredNotification;
import com.example.open_telemetry_example.infrastructure.adapter.config.pulsar.MessageUtils;
import com.example.open_telemetry_example.infrastructure.adapter.output.event.EventProducer;
import com.example.open_telemetry_example.infrastructure.adapter.output.event.PulsarMessageType;
import io.micrometer.tracing.annotation.NewSpan;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapPropagator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserRegistrationOutputAdapter implements UserRegistrationOutputPort {
    private final EventProducer eventProducer;
    private final TextMapPropagator propagator;

    @Override
    @NewSpan("pulsar-publish-user-registered")
    public void sendUserRegistrationNotification(RegisterUserCommand user) {
        String topic = MessageUtils
                .getTopicFromMessageType(PulsarMessageType.USER_REGISTRATION_NOTIFICATION);
        Map<String, String> traceHeaders = new HashMap<>();
        propagator.inject(Context.current(), traceHeaders, Map::put);

        UserRegisteredNotification payload =
                UserRegisteredNotification.builder()
                        .email(user.getUsername())
                        .createdAt(Instant.now())
                        .build();
        try {
            eventProducer.produceNotificationMessage(payload, topic, traceHeaders);
        } catch (PulsarClientException e) {
            log.error("Failed to publish UserRegisteredEvent", e);
            throw new RuntimeException(e);
        }

    }
}
