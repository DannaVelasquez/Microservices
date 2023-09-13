package com.dh.catalogservice.rabbitConfig;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSenderConfig {
    @Value("${queue.movie.name}")
    private String movieQueue;
    @Value("${queue.serie.name}")
    private String serieQueue;

    @Bean (name = "movieQueue")
    public Queue movieQueue() {
        return new Queue(this.movieQueue,  true);
    }
    @Bean (name = "serieQueue")
    public Queue serieQueue() {
        return new Queue(this.serieQueue,  true);
    }




}
