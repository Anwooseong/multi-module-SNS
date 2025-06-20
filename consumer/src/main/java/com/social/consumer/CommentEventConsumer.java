package com.social.consumer;

import com.social.event.CommentEvent;
import com.social.event.CommentEventType;
import com.social.task.CommentAddTask;
import com.social.task.CommentRemoveTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentEventConsumer {

    private final CommentAddTask commentAddTask;
    private final CommentRemoveTask commentRemoveTask;

    @Bean(name = "comment")
    public Consumer<CommentEvent> comment() {  //application-event.yml에 등록된 function definition에 따라서 함수명 설정
        log.info("Kafka LikeEvent consumer comment");

        return event -> {
            log.info("Kafka LikeEvent consumer comment: {}", event);

            if (event.getType() == CommentEventType.ADD) {
                log.info("Processing CommentEvent: ADD for commentId {} on postId {} by userId {}", event.getCommentId(), event.getPostId(), event.getUserId()); // <-- 상세 로그
                commentAddTask.processEvent(event); // <-- 이 메서드 내부에서 오류 발생 가능성 높음
                log.info("CommentEvent ADD processing finished successfully."); // <-- 성공 로그
            } else if (event.getType() == CommentEventType.REMOVE) {
                log.info("Processing CommentEvent: REMOVE for commentId {} on postId {} by userId {}", event.getCommentId(), event.getPostId(), event.getUserId()); // <-- 상세 로그
                commentRemoveTask.processEvent(event); // <-- 이 메서드 내부에서 오류 발생 가능성 높음
                log.info("CommentEvent REMOVE processing finished successfully.");
            }
        };
    }
}
