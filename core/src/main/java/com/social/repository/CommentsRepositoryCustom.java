package com.social.repository;

import com.social.repository.querydslDTO.GetPostsDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentsRepositoryCustom {
    Slice<GetPostsDTO> findByPost(Long userId, Long postId, Pageable pageable);
}
