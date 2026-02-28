package com.example.fraudservice.repository;

import com.example.fraudservice.model.FraudUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudUserRepository
        extends JpaRepository<FraudUser, Long> {
}