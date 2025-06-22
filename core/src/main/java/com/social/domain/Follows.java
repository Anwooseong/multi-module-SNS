package com.social.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"FROM_USER", "TO_USER"}
                )
        }
)
public class Follows extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_USER")
    private Users fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_USER")
    private Users toUser;

    @Builder
    public Follows(Long id, Users fromUser, Users toUser) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
