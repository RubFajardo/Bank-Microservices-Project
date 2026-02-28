package com.example.transactionservice.messaging.publisher;

import com.example.transactionservice.config.RabbitMQConfig;
import com.example.transactionservice.messaging.event.TransactionEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishTransactionCreated(TransactionEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TRANSACTION_EXCHANGE,
                "",
                event
        );
    }

}
