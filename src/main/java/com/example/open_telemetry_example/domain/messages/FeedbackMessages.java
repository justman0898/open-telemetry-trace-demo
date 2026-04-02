package com.example.open_telemetry_example.domain.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackMessages {
    public static final String OPERATION_SUCCESSFUL_FEEDBACK_MESSAGE
            = "Operation successfully completed";
}
