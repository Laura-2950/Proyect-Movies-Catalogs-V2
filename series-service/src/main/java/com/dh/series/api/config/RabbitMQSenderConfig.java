package com.dh.series.api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSenderConfig {

    @Value("${queue1.series.name}")
    private String seriesQueue;

    @Bean
    public Queue queue() {
        return new Queue(this.seriesQueue, true);
    }
}
