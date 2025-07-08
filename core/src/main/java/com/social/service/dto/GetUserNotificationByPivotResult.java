package com.social.service.dto;

import com.social.notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetUserNotificationByPivotResult {
    private List<Notification> notifications;
    private boolean hasNext;
}
