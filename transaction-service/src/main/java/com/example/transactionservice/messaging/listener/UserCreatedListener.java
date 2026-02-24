package com.example.transactionservice.messaging.listener;

import com.example.transactionservice.config.RabbitMQConfig;
import com.example.transactionservice.messaging.event.UserCreatedEvent;
import com.example.transactionservice.model.Account;
import com.example.transactionservice.model.TransactionUser;
import com.example.transactionservice.repository.AccountRepository;
import com.example.transactionservice.repository.TransactionUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedListener {

    @Autowired
    private TransactionUserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private static final Logger log = LoggerFactory.getLogger(UserCreatedListener.class);

    @RabbitListener(queuesToDeclare = @Queue(value = RabbitMQConfig.USER_CREATED_QUEUE, durable = "true"))
    public void handle(UserCreatedEvent event) {

        log.info("Evento recibido: Usuario creado con email [{}]", event.getEmail());
        try {
            TransactionUser user = new TransactionUser(event.getId(), event.getEmail(), event.getName());
            userRepository.save(user);
            Account account = new Account(user.getId());
            accountRepository.save(account);
        } catch (Exception e) {
            log.error("Error procesando evento de usuario: {}", e.getMessage());
            throw e;
        }

    }
}