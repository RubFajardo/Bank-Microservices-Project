package com.example.transactionservice.service;

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
}
