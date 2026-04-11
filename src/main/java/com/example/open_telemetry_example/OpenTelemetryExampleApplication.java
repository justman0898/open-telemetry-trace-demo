package com.example.open_telemetry_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OpenTelemetryExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenTelemetryExampleApplication.class, args);
	}

}
