package com.social.repository;

import com.social.domain.Posts;
import com.social.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsRepositoryCustom {

    @Query("SELECT p FROM Posts p JOIN FETCH p.photos ph WHERE p.id = :postId ORDER BY ph.sortOrder ASC")
    Optional<Posts> findWithPhotosByIdOrderBySortOrderAsc(@Param("postId") Long postId);

    @Query("SELECT p FROM Posts p JOIN FETCH p.user")
    Optional<Posts> findWithUserById(Long id);

    Optional<Posts> findByIdAndUser(Long id, Users user);
}
