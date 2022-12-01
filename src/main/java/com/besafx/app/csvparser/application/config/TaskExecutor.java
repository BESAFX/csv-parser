package com.besafx.app.csvparser.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class TaskExecutor {

    @Value("${executor.defaultPoolSize:5}")
    private int defaultPoolSize;

    @Value("${executor.maxPoolSize:10}")
    private int maxPoolSize;

    @Bean("csvParser")
    public Executor csvParser() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(defaultPoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("CSV-");
        executor.initialize();
        return executor;
    }

    @Bean("lineAppender")
    public Executor lineAppender() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(defaultPoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("LINE-");
        executor.initialize();
        return executor;
    }
}
