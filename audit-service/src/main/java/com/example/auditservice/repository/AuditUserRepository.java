package com.example.auditservice.repository;

import com.example.auditservice.model.AuditUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditUserRepository
        extends JpaRepository<AuditUser, Long> {
}