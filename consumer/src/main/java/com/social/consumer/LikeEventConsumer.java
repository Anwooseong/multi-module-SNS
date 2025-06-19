package com.social.consumer;

import com.social.event.LikeEvent;
import com.social.event.LikeEventType;
import com.social.task.LikeAddTask;
import com.social.task.LikeRemoveTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeEventConsumer {

    private final LikeAddTask likeAddTask;
    private final LikeRemoveTask likeRemoveTask;

    @Bean(name = "like")
    public Consumer<LikeEvent> like() {  //application-event.yml 에 등록된 function definition 에 따라서 함수명 설정
        log.info("Kafka LikeEvent consumer like");
        return event -> {
            log.info("Kafka LikeEvent consumer like: {}", event);

            if (event.getType() == LikeEventType.ADD) {
                likeAddTask.processEvent(event);
            } else if (event.getType() == LikeEventType.REMOVE) {
                likeRemoveTask.processEvent(event);
            }
        };
    }
}
