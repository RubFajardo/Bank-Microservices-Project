package com.example.authservice.messaging.publisher;

import com.example.authservice.config.RabbitMQConfig;
import com.example.authservice.messaging.event.UserCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishUserCreated(UserCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.USER_EXCHANGE,
                RabbitMQConfig.USER_CREATED_KEY,
                event
        );
    }
}
