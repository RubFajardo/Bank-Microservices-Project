package com.example.transactionservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private TransactionUser user;

    public Account () {}

    public Account (TransactionUser user) {
        this.user = user;
        this.balance = 0L;
    }

    public TransactionUser getUser() {
        return user;
    }

    public void setUser(TransactionUser user) { this.user = user; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
