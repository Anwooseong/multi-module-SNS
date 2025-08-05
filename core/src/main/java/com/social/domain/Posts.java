package com.social.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;

    private String caption;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photos> photos = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();

    @Builder
    public Posts(Long id, Users user, String caption) {
        this.id = id;
        this.user = user;
        this.caption = caption;
    }

    // -- 연관관계 메서드 -- //
    public void clearPhotos() {
        for (Photos photo : this.photos) {
            photo.initPost(null);
        }
        this.photos.clear();
    }

    public void addPhoto(Photos photo) {
        this.photos.add(photo);
        if (photo.getPost() != this) {
            photo.initPost(this);
        }
    }

    public Posts update(Posts request, List<String> imageUrls) {
        this.caption = request.getCaption();

        clearPhotos();

        if (imageUrls != null && !imageUrls.isEmpty()) {
            IntStream.range(0, imageUrls.size()) // 0부터 imageUrls.size()까지의 인덱스 스트림 생성
                    .mapToObj(index -> {
                        String imageUrl = imageUrls.get(index);
                        return Photos.builder()
                                .post(this)
                                .imageUrl(imageUrl)
                                .sortOrder(index)
                                .build();
                    })
                    .forEach(this::addPhoto);
        }

        return this;
    }
}
