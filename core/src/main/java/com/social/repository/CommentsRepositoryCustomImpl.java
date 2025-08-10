package com.social.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.domain.QComments;
import com.social.domain.QUsers;
import com.social.repository.querydslDTO.GetCommentDTO;
import com.social.repository.querydslDTO.GetPostsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentsRepositoryCustomImpl implements CommentsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GetPostsDTO> findByPost(Long userId, Long postId, Pageable pageable) {

        List<GetCommentDTO> results = queryFactory
                .select(Projections.constructor(GetCommentDTO.class,
                        QUsers.users.id,
                        QUsers.users.username,
                        QComments.comments.id,
                        QComments.comments.content,
                        QComments.comments.updateAt
                ))
                .from(QComments.comments)
                .where()
                .orderBy(QComments.comments.updateAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return null;
    }
}
