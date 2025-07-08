package com.social.service;

import com.social.notification.domain.Notification;
import com.social.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSaveService {

    private final NotificationRepository notificationRepository;

    public void insert(Notification notification) {
        Notification result = notificationRepository.insert(notification);
    }

    public void upsert(Notification notification) {
        Notification result = notificationRepository.save(notification);
    }

}
