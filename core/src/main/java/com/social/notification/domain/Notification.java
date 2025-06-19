package com.social.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("notifications")
public class Notification {
    @Field(targetType = FieldType.STRING)
    private String id;
    private Long userId;
    private NotificationType type;
    private Instant occurredAt;
    private Instant createdAt;
    private Instant lastUpdatedAt;
    private Instant deletedAt;
}
