package com.social.service;

import com.social.notification.domain.CommentNotification;
import com.social.notification.domain.FollowNotification;
import com.social.notification.domain.LikeNotification;
import com.social.service.convert.CommentUserNotificationConverter;
import com.social.service.convert.FollowUserNotificationConverter;
import com.social.service.convert.LikeUserNotificationConverter;
import com.social.service.dto.ConvertedNotification;
import com.social.service.dto.GetUserNotificationByPivotResult;
import com.social.service.dto.GetUserNotificationsResult;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class GetUserNotificationsService {

    private final NotificationListService listService;
    private final CommentUserNotificationConverter commentConverter;
    private final LikeUserNotificationConverter likeConverter;
    private final FollowUserNotificationConverter followConverter;

    public GetUserNotificationsService(NotificationListService listService, CommentUserNotificationConverter commentConverter, LikeUserNotificationConverter likeConverter, FollowUserNotificationConverter followConverter) {
        this.listService = listService;
        this.commentConverter = commentConverter;
        this.likeConverter = likeConverter;
        this.followConverter = followConverter;
    }

    public GetUserNotificationsResult getUserNotificationByPivot(long userId, Instant pivot) {
        GetUserNotificationByPivotResult result = listService.getUserNotificationByPivot(userId, pivot);

        List<ConvertedNotification> convertedNotifications = result.getNotifications().stream()
                .map(notification -> switch (notification.getType()) {
                    case COMMENT -> commentConverter.convert((CommentNotification) notification);
                    case LIKE -> likeConverter.convert((LikeNotification) notification);
                    case FOLLOW -> followConverter.convert((FollowNotification) notification);
                })
                .toList();

        return new GetUserNotificationsResult(
                convertedNotifications,
                result.isHasNext()
        );
    }
}
