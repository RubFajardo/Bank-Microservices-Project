package com.example.notificationsservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications_users")
public class NotificationUser {

    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;


    public NotificationUser() {}

    public NotificationUser(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
}
