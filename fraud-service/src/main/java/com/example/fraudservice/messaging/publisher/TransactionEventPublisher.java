package com.example.fraudservice.messaging.publisher;

import com.example.fraudservice.config.RabbitMQConfig;
import com.example.fraudservice.messaging.event.TransactionUpdated;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishTransactionUpdated(TransactionUpdated event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TRANSACTION_EXCHANGE,
                "",
                event
        );
    }
}