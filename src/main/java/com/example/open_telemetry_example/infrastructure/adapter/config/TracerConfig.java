package com.example.open_telemetry_example.infrastructure.adapter.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracerConfig {

    @Bean
    public Tracer tracer(@Qualifier("openTelemetry") OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("user-lifecycle");
    }


}
