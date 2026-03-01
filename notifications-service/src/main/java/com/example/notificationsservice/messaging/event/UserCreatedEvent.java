package com.example.notificationsservice.messaging.event;

import java.io.Serializable;

public class UserCreatedEvent implements Serializable {

    private Long id;
    private String email;
    private String name;

    public UserCreatedEvent() {}

    public UserCreatedEvent(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
}