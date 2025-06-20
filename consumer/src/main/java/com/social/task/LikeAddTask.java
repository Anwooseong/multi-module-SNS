package com.social.task;

import com.social.domain.Posts;
import com.social.event.LikeEvent;
import com.social.notification.domain.LikeNotification;
import com.social.notification.domain.Notification;
import com.social.notification.domain.NotificationType;
import com.social.repository.PostsRepository;
import com.social.service.NotificationGetService;
import com.social.service.NotificationSaveService;
import com.social.utils.NotificationIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeAddTask {

    private final PostsRepository postsRepository;
    private final NotificationGetService notificationGetService;
    private final NotificationSaveService notificationSaveService;

    public void processEvent(LikeEvent event) {
        // 자신의 게시글인 경우 무시
        Posts post = postsRepository.findWithUserById(event.getPostId())
                .orElseThrow(() -> new RuntimeException("Post is null with postId:"+ event.getPostId()));

        if (Objects.equals(post.getId(), event.getUserId())) {
            log.info("자기 게시글 좋아요 추가는 인한 알림은 등록 X");
            return;
        }

        Notification notification = createOrUpdateNotification(event, post);
        notificationSaveService.upsert(notification);
    }

    private Notification createOrUpdateNotification(LikeEvent event, Posts post) {
        Optional<Notification> optionalNotification = notificationGetService.getNotificationByTypeAndPostId(NotificationType.LIKE, post.getId());

        Instant now = Instant.now();
        Instant retention = Instant.now().plus(90, ChronoUnit.DAYS);

        if (optionalNotification.isPresent()) {
            // 업데이트
            return updateNotification((LikeNotification) optionalNotification.get(), event, now, retention);
        } else {
            // 신규 생성
            return createNotification(event, post, now, retention);
        }
    }

    private Notification updateNotification(LikeNotification notification, LikeEvent event, Instant now, Instant retention) {
        notification.addLiker(event.getUserId(), event.getCreatedAt(), now, retention);
        return notification;
    }

    private Notification createNotification(LikeEvent event, Posts post, Instant now, Instant retention) {
        return new LikeNotification(
                NotificationIdGenerator.generate(),
                post.getUser().getId(),
                NotificationType.LIKE,
                event.getCreatedAt(),
                now,
                now,
                retention,
                post.getId(),
                List.of(event.getUserId())
        );
    }
}
