package com.social.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEvent {
    private CommentEventType type;
    private Long postId;
    private Long userId;
    private Long commentId;
}
