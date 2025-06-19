package com.social.service;

import com.social.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRemoveService {

    private final NotificationRepository notificationRepository;

    public void deleteById(String id) {
        log.info("Notification removed: {}", id);
        notificationRepository.deleteById(id);
    }
}
