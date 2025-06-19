package com.social.listener;

import com.social.event.LikeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class EventListener {

    private final Consumer<LikeEvent> likeEventConsumer;

    @KafkaListener(groupId = "notification-consumer", topics = "like")
    public void like(LikeEvent event) {
        likeEventConsumer.accept(event);
    }
}
