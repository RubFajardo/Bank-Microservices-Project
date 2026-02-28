package com.example.transactionservice.messaging.listener;

import com.example.transactionservice.config.RabbitMQConfig;
import com.example.transactionservice.exception.NotFoundException;
import com.example.transactionservice.messaging.event.TransactionUpdated;
import com.example.transactionservice.model.Account;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.TransactionStatus;
import com.example.transactionservice.repository.AccountRepository;
import com.example.transactionservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionUpdatedListener {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    @RabbitListener(queues = RabbitMQConfig.TRANSACTION_UPDATED_CORE_QUEUE)
    @Transactional
    public void handleTransactionUpdate(TransactionUpdated event) {

        Transaction tr = transactionRepository.findById(event.getId())
                .orElseThrow(() -> new NotFoundException("Transacción no encontrada"));

        if (tr.getStatus() != TransactionStatus.PENDING) {
            return;
        }

        if (event.getStatus() == TransactionStatus.COMPLETED) {
            executeTransfer(tr);
        } else {
            tr.setStatus(TransactionStatus.REJECTED);
        }

        transactionRepository.save(tr);
    }

    private void executeTransfer(Transaction tr) {
        Account source = accountRepository.findById(tr.getSourceAccountId())
                .orElseThrow(() -> new NotFoundException("Cuenta origen no encontrada"));
        Account dest = accountRepository.findById(tr.getDestinationAccountId())
                .orElseThrow(() -> new NotFoundException("Cuenta destino no encontrada"));

        source.setBalance(source.getBalance() - tr.getAmount());
        dest.setBalance(dest.getBalance() + tr.getAmount());

        accountRepository.save(source);
        accountRepository.save(dest);
    }
}