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
        return event -> {
            if (event.getType() == CommentEventType.ADD) {
                commentAddTask.processEvent(event); // <-- 이 메서드 내부에서 오류 발생 가능성 높음
            } else if (event.getType() == CommentEventType.REMOVE) {
                commentRemoveTask.processEvent(event);
            }
        };
    }
}
