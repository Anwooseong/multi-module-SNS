package com.social.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.domain.QPhotos;
import com.social.repository.querydslDTO.GetPostsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.social.domain.QComments.comments;
import static com.social.domain.QLikes.likes;
import static com.social.domain.QPhotos.*;
import static com.social.domain.QPosts.posts;

@Repository
@RequiredArgsConstructor
public class PostsRepositoryCustomImpl implements PostsRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GetPostsDTO> findPostsSummaries(Long userId, Pageable pageable) {
        QPhotos photosSub = new QPhotos("photosSub");

        List<GetPostsDTO> results = queryFactory
                .select(Projections.constructor(GetPostsDTO.class,
                        posts.id,
                        posts.caption,
                        likes.isNotNull(),
                        comments.countDistinct(),
                        posts.createAt,
                        JPAExpressions.select(photos.imageUrl)
                                .from(photos)
                                .where(
                                        photos.post.id.eq(posts.id),
                                        photos.sortOrder.eq(
                                                JPAExpressions.select(photosSub.sortOrder.min())
                                                        .from(photosSub)
                                                        .where(photosSub.post.id.eq(posts.id))
                                        )
                                )
                ))
                .from(posts)
                .leftJoin(likes).on(likes.post.id.eq(posts.id).and(likes.user.id.eq(userId)))
                .leftJoin(comments).on(comments.post.id.eq(posts.id))
                .groupBy(posts.id, posts.caption, likes.id, posts.createAt)
                .orderBy(posts.createAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        if (hasNext) {
            results = results.subList(0, pageable.getPageSize());
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

}
