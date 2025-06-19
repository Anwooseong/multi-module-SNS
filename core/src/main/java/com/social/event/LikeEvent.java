package com.social.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeEvent {
    private LikeEventType type;
    private Long postId;
    private Long userId;
    private Instant createdAt;
}
