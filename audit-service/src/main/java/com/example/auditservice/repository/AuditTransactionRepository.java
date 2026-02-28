package com.example.auditservice.repository;

import com.example.auditservice.model.AuditTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTransactionRepository
        extends JpaRepository<AuditTransaction, Long> {
}
