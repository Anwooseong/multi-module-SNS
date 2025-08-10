package com.social.repository;

import com.social.domain.Comments;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Long>, CommentsRepositoryCustom {

    @Query("SELECT c FROM Comments c JOIN FETCH c.user AND JOIN FETCH c.post WHERE c.id = :id")
    Optional<Comments> findWithUserAndPostById(@Param("id") Long id);

    @Query("SELECT c FROM Comments c WHERE c.id = :id AND c.user.id = :userId AND c.post.id = :postId")
    Optional<Comments> findByIdAndUserIdAndPostId(@Param("id") Long id, @Param("userId") Long userId, @Param("postId") Long postId);


    @Query("SELECT c FROM Comments c JOIN FETCH c.user WHERE c.post.id = :postId ORDER BY c.updateAt DESC")
    Slice<Comments> findSliceByPostId(@Param("postId") Long postId, Pageable pageable);

    void deleteAllByPostId(Long postId);
}
