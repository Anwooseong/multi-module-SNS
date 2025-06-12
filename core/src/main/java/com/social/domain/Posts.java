package com.social.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;

    private String caption;

    @OneToMany(mappedBy = "post")
    private List<Photos> photos = new ArrayList<>();

    @Builder
    public Posts(Long id, Users user, String caption) {
        this.id = id;
        this.user = user;
        this.caption = caption;
    }
}
