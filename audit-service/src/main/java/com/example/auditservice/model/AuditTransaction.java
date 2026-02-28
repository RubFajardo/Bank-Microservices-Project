package com.example.auditservice.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_transactions")
public class AuditTransaction {

    @Id
    private Long id;

    @Column(name = "sourceAccountId")
    private Long sourceAccountId;

    @Column(name = "destinationAccountId")
    private Long destinationAccountId;

    @Column(name = "amount")
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "audit_date")
    private LocalDateTime auditDate;

    public AuditTransaction() {}

    public AuditTransaction(Long id, Long sourceAccountId, Long destinationAccountId, Long amount, TransactionStatus status) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.status = status;
        this.auditDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getSourceAccountId() { return sourceAccountId; }
    public Long getDestinationAccountId() { return destinationAccountId; }
    public Long getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }
}
