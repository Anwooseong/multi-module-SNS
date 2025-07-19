package com.social.service;

import com.social.notification.repository.NotificationReadRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class NotificationLastReadAtService {

    private final NotificationReadRepository repository;

    public NotificationLastReadAtService(NotificationReadRepository repository){
        this.repository = repository;
    }

    public Instant setLastReadAt(long userId) {
        return repository.setLastReadAt(userId);
    }

    public Instant getLastReadAt(long userId) {
        return repository.getLastReadAt(userId);
    }
}
