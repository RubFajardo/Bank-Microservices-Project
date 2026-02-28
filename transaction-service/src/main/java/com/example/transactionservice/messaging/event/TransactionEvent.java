package com.example.transactionservice.messaging.event;

import com.example.transactionservice.model.TransactionStatus;

import java.io.Serializable;

public class TransactionEvent implements Serializable {

    private Long id;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private TransactionStatus status;
    private Long amount;

    public TransactionEvent () {}

    public TransactionEvent (Long id, Long sourceAccountId, Long destinationAccountId, Long amount, TransactionStatus status) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.status = TransactionStatus.PENDING;
        this.amount = amount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public Long getAmount() {
        return amount;
    }
    public Long getDestinationAccountId() {
        return destinationAccountId;
    }
    public Long getSourceAccountId() {
        return sourceAccountId;
    }
    public TransactionStatus getStatus() {
        return status;
    }

}
