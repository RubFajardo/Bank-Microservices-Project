package com.example.notificationsservice.messaging.listener;

import com.example.notificationsservice.config.RabbitMQConfig;
import com.example.notificationsservice.exception.NotFoundException;
import com.example.notificationsservice.messaging.event.TransactionUpdated;
import com.example.notificationsservice.model.NotificationUser;
import com.example.notificationsservice.model.TransactionStatus;
import com.example.notificationsservice.repository.NotificationUserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionUpdatedListener {

    private static final Logger log = LoggerFactory.getLogger(TransactionUpdatedListener.class);

    @Autowired
    private NotificationUserRepository userRepository;

    @RabbitListener(queues = RabbitMQConfig.TRANSACTION_UPDATED_NOTIFICATION_QUEUE)
    @Transactional
    public void handleTransactionUpdate(TransactionUpdated event) {

        NotificationUser user = userRepository.findById(event.getSourceAccountId()).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (event.getStatus() == TransactionStatus.COMPLETED) {
            log.info("[SMS/Email] Tu transferencia con ID {} ha sido procesada exitosamente.", event.getId());
            System.out.println("Hola " + user.getName() + ", tu transferencia " + event.getId() + " ha sido procesada correctamente.");
        } else {
            log.warn("[SMS/Email] Tu transferencia con ID {} ha sido rechazada por seguridad. Contacta a tu banco para más información.", event.getId());
            System.out.println("Hola " + user.getName() + ", tu transferencia " + event.getId() + " ha sido retenida temporalemente.");
        }
    }
}