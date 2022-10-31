package com.digitalhouse.catalogservice.api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {

    @Value("${queue.movie.name}")
    private String movieQueue;

    @Value("${queue1.series.name}")
    private String seriesQueue;

    @Bean
    public Queue queue() {
        return new Queue(this.movieQueue, true);
    }

    @Bean
    public Queue queue1(){
        return new Queue(this.seriesQueue,true);
    }
}
