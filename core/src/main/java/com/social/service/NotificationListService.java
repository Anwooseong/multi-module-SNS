package com.social.service;

import com.social.notification.domain.Notification;
import com.social.notification.repository.NotificationRepository;
import com.social.service.dto.GetUserNotificationByPivotResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NotificationListService {

    private final NotificationRepository notificationRepository;

    public GetUserNotificationByPivotResult getUserNotificationByPivot(Long userId, Instant occurredAt) {
        Slice<Notification> result;
        if (occurredAt == null) {
            result = notificationRepository.findAllByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(0, PAGE_SIZE));
        }else {
            result = notificationRepository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        }

        return new GetUserNotificationByPivotResult(
                result.getContent(),
                result.hasNext()
        );
    }

    private static final int PAGE_SIZE = 20;
}
