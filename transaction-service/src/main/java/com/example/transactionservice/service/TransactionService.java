package com.example.transactionservice.service;

import com.example.transactionservice.exception.InsufficientFundsException;
import com.example.transactionservice.exception.NotFoundException;
import com.example.transactionservice.model.Account;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.TransactionStatus;
import com.example.transactionservice.repository.AccountRepository;
import com.example.transactionservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final RabbitTemplate rabbitTemplate;

    public TransactionService (AccountRepository accountRepository, TransactionRepository transactionRepository, RabbitTemplate rabbitTemplate) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void deposit(Long destAccountId, Long amount) {
        Account destination = accountRepository.findById(destAccountId).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        destination.setBalance(destination.getBalance() + amount);
        accountRepository.save(destination);

        Transaction tr = new Transaction(null, destination.getId(), amount);
        tr.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(tr);
    }

    @Transactional
    public void withdrawal (Long destAccountId, Long amount) {
        Account destination = accountRepository.findById(destAccountId).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        if (destination.getBalance() < amount) {
            throw new InsufficientFundsException("Saldo insuficiente");
        }

        destination.setBalance(destination.getBalance() - amount);
        accountRepository.save(destination);

        Transaction tr = new Transaction(destination.getId(), null, amount);
        tr.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(tr);
    }

    @Transactional
    public void transfer(Long sourceAccountId, Long destAccountId, Long amount) {

        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new NotFoundException("Cuenta origen no existe"));
        Account destination = accountRepository.findById(destAccountId)
                .orElseThrow(() -> new NotFoundException("Cuenta destino no existe"));

        if (source.getBalance() < amount) {
            throw new InsufficientFundsException("Saldo insuficiente");
        }

        source.setBalance(source.getBalance() - amount);
        destination.setBalance(destination.getBalance() + amount);

        Transaction tr = new Transaction(sourceAccountId, destAccountId, amount);
        tr.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(tr);

    }


}
