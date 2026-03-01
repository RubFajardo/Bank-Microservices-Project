package com.example.notificationsservice.repository;

import com.example.notificationsservice.model.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationUserRepository
        extends JpaRepository<NotificationUser, Long> {
}