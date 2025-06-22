package com.social.consumer;

import com.social.event.FollowEvent;
import com.social.event.FollowEventType;
import com.social.task.FollowAddTask;
import com.social.task.FollowRemoveTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowEventConsumer {

    private final FollowAddTask followAddTask;
    private final FollowRemoveTask followRemoveTask;

    @Bean(name = "follow")
    public Consumer<FollowEvent> follow() {  //application-event.yml 에 등록된 function definition 에 따라서 함수명 설정
        return event -> {
            if (event.getType() == FollowEventType.ADD) {
                followAddTask.processEvent(event);
            }else if(event.getType() == FollowEventType.REMOVE){
                followRemoveTask.processEvent(event);
            }
        };
    }
}
