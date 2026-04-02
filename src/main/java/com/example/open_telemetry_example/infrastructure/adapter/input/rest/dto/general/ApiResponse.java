package com.example.open_telemetry_example.infrastructure.adapter.input.rest.dto.general;

import com.example.open_telemetry_example.domain.messages.FeedbackMessages;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public class ApiResponse<T> {
    @JsonProperty("isSuccessful")
    private boolean isSuccessful;
    private String message;
    private int status;
    private T data;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXXXX'['VV']'")
    private ZonedDateTime timeStamp;


    public static Object ok(Object toBeWrapped) {
        return ApiResponse.builder()
                .isSuccessful(true)
                .message(FeedbackMessages.OPERATION_SUCCESSFUL_FEEDBACK_MESSAGE)
                .status(200)
                .data(toBeWrapped)
                .timeStamp(ZonedDateTime.now());
    }
}
