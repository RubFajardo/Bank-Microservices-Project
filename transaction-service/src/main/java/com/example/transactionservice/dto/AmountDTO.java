package com.example.transactionservice.dto;

public class AmountDTO {

    private Long amount;

    public AmountDTO () {}

    public AmountDTO (Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
