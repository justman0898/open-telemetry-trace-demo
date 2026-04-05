package com.example.open_telemetry_example.infrastructure.adapter.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.propagation.TextMapPropagator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropagatorConfig {

    @Bean
    public TextMapPropagator textMapPropagator(@Qualifier("openTelemetry") OpenTelemetry openTelemetry) {
        return openTelemetry.getPropagators().getTextMapPropagator();
    }
}
