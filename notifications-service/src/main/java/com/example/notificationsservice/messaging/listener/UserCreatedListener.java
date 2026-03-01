package com.example.notificationsservice.messaging.listener;

import com.example.notificationsservice.config.RabbitMQConfig;
import com.example.notificationsservice.messaging.event.UserCreatedEvent;
import com.example.notificationsservice.model.NotificationUser;
import com.example.notificationsservice.repository.NotificationUserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedListener {

    @Autowired
    private NotificationUserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserCreatedListener.class);

    @RabbitListener(queues = RabbitMQConfig.USER_CREATED_QUEUE)
    @Transactional
    public void handle(UserCreatedEvent event) {

        log.info("Evento recibido: Usuario creado con email [{}]", event.getEmail());
        try {
            NotificationUser user = new NotificationUser(event.getId(), event.getEmail(), event.getName());
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error procesando evento de usuario: {}", e.getMessage());
            throw e;
        }
    }
}