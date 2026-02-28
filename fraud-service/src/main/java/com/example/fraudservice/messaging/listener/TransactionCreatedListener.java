package com.example.fraudservice.messaging.listener;

import com.example.fraudservice.config.RabbitMQConfig;
import com.example.fraudservice.messaging.event.TransactionEvent;
import com.example.fraudservice.messaging.event.TransactionUpdated;
import com.example.fraudservice.messaging.publisher.TransactionEventPublisher;
import com.example.fraudservice.model.FraudTransaction;
import com.example.fraudservice.model.TransactionStatus;
import com.example.fraudservice.repository.FraudTransactionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionCreatedListener {

    @Autowired
    private FraudTransactionRepository transactionRepository;

    @Autowired
    private TransactionEventPublisher eventPublisher;

    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedListener.class);

    @RabbitListener(queues = RabbitMQConfig.FRAUD_QUEUE)
    @Transactional
    public void handle(TransactionEvent event) {

        log.info("Evento recibido: Transacción creada con id [{}]", event.getId());
        try {
            FraudTransaction tr = new FraudTransaction(event.getId(), event.getSourceAccountId(), event.getDestinationAccountId(), event.getAmount(), event.getStatus());
            tr.setStatus(tr.getAmount() > 10000 ? TransactionStatus.REJECTED : TransactionStatus.COMPLETED);
            transactionRepository.save(tr);

            TransactionUpdated update = new TransactionUpdated(tr.getId(), tr.getSourceAccountId(), tr.getDestinationAccountId(), tr.getAmount(), tr.getStatus());
            eventPublisher.publishTransactionUpdated(update);
        } catch (Exception e) {
            log.error("Error procesando evento de transacción: {}", e.getMessage());
            throw e;
        }
    }
}
