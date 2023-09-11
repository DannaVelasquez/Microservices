package com.dh.catalogservice.queue;

import com.dh.catalogservice.model.Movie;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MovieSender {

    private final RabbitTemplate rabbitTemplate;

    private final Queue movieQueue;

    public MovieSender(RabbitTemplate rabbitTemplate, Queue movieQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.movieQueue = movieQueue;
    }


    public void send(Movie movie) {
        this.rabbitTemplate.convertAndSend(this.movieQueue.getName(), movie);
    }

}
