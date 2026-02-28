package com.example.auditservice.messaging.listener;

import com.example.auditservice.config.RabbitMQConfig;
import com.example.auditservice.messaging.event.TransactionEvent;
import com.example.auditservice.messaging.event.UserCreatedEvent;
import com.example.auditservice.model.AuditTransaction;
import com.example.auditservice.model.AuditUser;
import com.example.auditservice.repository.AuditTransactionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionCreatedListener {

    @Autowired
    private AuditTransactionRepository transactionRepository;

    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedListener.class);

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    @Transactional
    public void handle(TransactionEvent event) {

        log.info("Evento recibido: Transacción creada con id [{}]", event.getId());
        try {
            AuditTransaction transaction = new AuditTransaction(event.getId(), event.getSourceAccountId(), event.getDestinationAccountId(), event.getAmount(), event.getStatus());
            transactionRepository.save(transaction);
        } catch (Exception e) {
            log.error("Error procesando evento de transacción: {}", e.getMessage());
            throw e;
        }
    }
}
