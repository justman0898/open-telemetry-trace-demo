package com.example.open_telemetry_example.infrastructure.adapter.config.pulsar;

import com.example.open_telemetry_example.infrastructure.adapter.output.event.PulsarMessageType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {

    public static String getTopicFromMessageType(PulsarMessageType messageType) {
        return switch (messageType) {
            case USER_REGISTRATION_NOTIFICATION -> "user-registration-notification";
        };
    }
}
