package com.social.task;

import com.social.domain.Posts;
import com.social.event.CommentEvent;
import com.social.notification.domain.NotificationType;
import com.social.repository.PostsRepository;
import com.social.service.NotificationGetService;
import com.social.service.NotificationRemoveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentRemoveTask {

    private final PostsRepository postsRepository;
    private final NotificationGetService notificationGetService;
    private final NotificationRemoveService notificationRemoveService;

    public void processEvent(CommentEvent event) {
        // 자신의 댓글인 경우 무시
        Posts post = postsRepository.findWithUserById(event.getPostId())
                .orElseThrow(() -> new RuntimeException("Post is null with postId:"+ event.getPostId()));

        if (Objects.equals(post.getUser().getId(), event.getUserId())) {
            return;
        }

        notificationGetService.getNotificationByTypeAndCommentId(NotificationType.COMMENT, event.getCommentId())
                .ifPresentOrElse(
                        notification -> notificationRemoveService.deleteById(notification.getId()),
                        () -> log.error("Notification not found")
                );

    }
}
