package com.social.task;

import com.social.notification.domain.NotificationType;
import com.social.event.FollowEvent;
import com.social.service.NotificationGetService;
import com.social.service.NotificationRemoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowRemoveTask {

    private final NotificationGetService getService;

    private final NotificationRemoveService removeService;

    public void processEvent(FollowEvent event) {
        getService.getNotificationByTypeAndUserIdAndFollowerId(NotificationType.FOLLOW, event.getTargetUserId(),
                        event.getUserId())
                .ifPresent(
                        notification -> removeService.deleteById(notification.getId())
                );
    }
}
