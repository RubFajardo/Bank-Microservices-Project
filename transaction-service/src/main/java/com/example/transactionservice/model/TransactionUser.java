package com.example.transactionservice.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction_users")
public class TransactionUser {

    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    public TransactionUser () {}

    public TransactionUser (Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
}
