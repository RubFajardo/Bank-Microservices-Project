package com.example.fraudservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "audit_transactions")
public class FraudTransaction {

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

    public FraudTransaction() {}

    public FraudTransaction(Long id, Long sourceAccountId, Long destinationAccountId, Long amount, TransactionStatus status) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.status = status;
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

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
