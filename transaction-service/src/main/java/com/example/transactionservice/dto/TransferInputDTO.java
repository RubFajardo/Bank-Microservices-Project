package com.example.transactionservice.dto;

public class TransferInputDTO {

    private Long amount;
    private Long destAccountId;
    private Long sourceAccountId;

    public TransferInputDTO () {}

    public TransferInputDTO (Long sourceAccountId, Long destAccountId, Long amount) {
        this.amount = amount;
        this.destAccountId = destAccountId;
        this.sourceAccountId = sourceAccountId;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public Long getDestAccountId() {
        return destAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public void setDestAccountId(Long destAccountId) {
        this.destAccountId = destAccountId;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
