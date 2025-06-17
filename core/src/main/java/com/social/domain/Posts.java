package com.social.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Photos> photos = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();

    @Builder
    public Posts(Long id, Users user, String caption) {
        this.id = id;
        this.user = user;
        this.caption = caption;
    }
}
