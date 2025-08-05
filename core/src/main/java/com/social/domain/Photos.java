package com.social.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photos extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Posts post;

    private String imageUrl;
    private int sortOrder;

    @Builder
    public Photos(Long id, Posts post, String imageUrl, int sortOrder) {
        this.id = id;
        this.post = post;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
    }

    public void initPost(Posts posts) {
        this.post = posts;
    }
}
