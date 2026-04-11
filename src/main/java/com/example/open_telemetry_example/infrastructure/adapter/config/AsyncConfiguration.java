package com.example.open_telemetry_example.infrastructure.adapter.config;


import io.opentelemetry.context.Context;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfiguration {

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("otel-async-");
        executor.initialize();

        // wrap at SUBMISSION time — captures context when task is submitted ✅
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                // Context.current() called HERE — at submission time ✅
                Context capturedContext = Context.current();
                executor.execute(capturedContext.wrap(command));
            }
        };
    }
}