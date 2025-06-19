package com.social.repository;

import com.social.domain.Likes;
import com.social.domain.Posts;
import com.social.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByUserAndPost(Users user, Posts post);

    Long deleteByUserAndPost(Users user, Posts post);
}
