package com.example.auditservice.messaging.listener;

import com.example.auditservice.config.RabbitMQConfig;
import com.example.auditservice.exception.NotFoundException;
import com.example.auditservice.messaging.event.TransactionUpdated;
import com.example.auditservice.model.AuditTransaction;
import com.example.auditservice.model.TransactionStatus;
import com.example.auditservice.repository.AuditTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionUpdatedListener {

    @Autowired
    private AuditTransactionRepository transactionRepository;

    @RabbitListener(queues = RabbitMQConfig.TRANSACTION_UPDATED_AUDIT_QUEUE)
    @Transactional
    public void handleTransactionUpdate(TransactionUpdated event) {

        AuditTransaction tr = transactionRepository.findById(event.getId())
                .orElseThrow(() -> new NotFoundException("Transacción no encontrada"));

        if (tr.getStatus() != TransactionStatus.PENDING) {
            return;
        }

        tr.setStatus(event.getStatus());

        transactionRepository.save(tr);
    }
}