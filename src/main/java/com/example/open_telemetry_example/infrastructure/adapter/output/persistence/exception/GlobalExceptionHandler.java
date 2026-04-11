package com.example.open_telemetry_example.infrastructure.adapter.output.persistence.exception;

import com.example.open_telemetry_example.domain.exceptions.UserAlreadyExistsException;
import com.example.open_telemetry_example.infrastructure.adapter.input.rest.dto.general.ApiResponse;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private static ResponseEntity<ApiResponse<Object>> getBaseResponseEntity(
            Exception e, HttpStatus status) {
        log.error("Global exceptionHandler: {}" ,e);

        Span.current().setStatus(StatusCode.ERROR, e.getMessage());
        Span.current().setAttribute("error.message", e.getMessage());
        Span.current().recordException(e);


        return ResponseEntity
                .status(status)
                .body(
                        ApiResponse.builder()
                                .timeStamp(ZonedDateTime.now())
                                .isSuccessful(false)
                                .message(e.getMessage())
                                .status(status.value())
                                .build()
                );

    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExistsException(
            UserAlreadyExistsException e
    )
    {
        Span.current().setAttribute("error.type", "UserAlreadyExistsException");
        return getBaseResponseEntity(e, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(
            Exception e
    )
    {
        return getBaseResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
