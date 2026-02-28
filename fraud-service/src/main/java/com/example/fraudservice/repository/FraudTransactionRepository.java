package com.example.fraudservice.repository;

import com.example.fraudservice.model.FraudTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudTransactionRepository
        extends JpaRepository<FraudTransaction, Long> {
}
