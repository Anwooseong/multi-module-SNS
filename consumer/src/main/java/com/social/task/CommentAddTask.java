package com.social.task;

import com.social.domain.Comments;
import com.social.domain.Posts;
import com.social.event.CommentEvent;
import com.social.notification.domain.CommentNotification;
import com.social.notification.domain.Notification;
import com.social.notification.domain.NotificationType;
import com.social.repository.CommentsRepository;
import com.social.repository.PostsRepository;
import com.social.service.NotificationSaveService;
import com.social.utils.NotificationIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentAddTask {

    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    private final NotificationSaveService notificationSaveService;

    public void processEvent(CommentEvent event) {
        // 자신의 댓글인 경우 무시
        Posts post = postsRepository.findWithUserById(event.getPostId())
                .orElseThrow(() -> new RuntimeException("Post is null with postId:"+ event.getPostId()));

        if (Objects.equals(post.getUser().getId(), event.getUserId())) {
            log.info("자기 게시글 좋아요 추가는 인한 알림은 등록 X");
            return;
        }

        Comments comment = commentsRepository.findWithUserAndPostById(event.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment is null with commentId:" + event.getCommentId()));

        Notification notification = createNotification(post, comment);
        notificationSaveService.insert(notification);
    }

    public Notification createNotification(Posts post, Comments comment) {
        Instant now = Instant.now();

        return new CommentNotification(
                NotificationIdGenerator.generate(),
                post.getUser().getId(),
                NotificationType.COMMENT,
                comment.getCreateAt().atZone(ZoneId.systemDefault()).toInstant(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                post.getId(),
                comment.getUser().getId(),
                comment.getContent(),
                comment.getId()
        );
    }
}
