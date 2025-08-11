package com.social.repository;

import com.social.config.TestJpaConfig;
import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.repository.querydslDTO.GetPostsDTO;
import com.social.util.*;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestJpaConfig.class)
class PostsRepositoryCustomTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsRepositoryCustomImpl postsRepositoryCustomImpl;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PhotosRepository photosRepository;

    @AfterEach
    void tearDown() {
        likesRepository.deleteAllInBatch();
        commentsRepository.deleteAllInBatch();
        photosRepository.deleteAllInBatch();
        postsRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();
    }

    @DisplayName("게시글 조회 테스트")
    @Test
    void findPostsSummaries() {
        // given
        Users user1 = usersRepository.save(UserFixture.user("user1"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user1, "test2"));

        commentsRepository.save(CommentFixture.comment(user1, post1, "댓글1"));
        commentsRepository.save(CommentFixture.comment(user1, post1, "댓글2"));

        likesRepository.save(LikeFixture.like(user1, post1));

        photosRepository.save(PhotoFixture.photo(post1, "url1", 1));
        photosRepository.save(PhotoFixture.photo(post1, "url1", 2));
        photosRepository.save(PhotoFixture.photo(post2, "url1", 1));

        Pageable pageable = PageRequest.of(0, 5);

        // when
        Slice<GetPostsDTO> result = postsRepositoryCustomImpl.findPostsSummaries(user1.getId(), pageable);
        System.out.println("총 개수: " + result.getContent().size());

        // then
        Assertions.assertThat(result.getContent()).hasSize(2);
        Assertions.assertThat(result.hasNext()).isFalse();
    }

    @Test
    void testFindPostsSummaries_whenMoreThanPageSize_thenHasNextIsTrue() {
        // given
        Users user = usersRepository.save(UserFixture.user("user2"));

        for (int i = 0; i < 7; i++) {
            Posts post = postsRepository.save(PostFixture.post(user, "post " + i));
            photosRepository.save(PhotoFixture.photo(post, "url" + i, 1));
        }

        Pageable pageable = PageRequest.of(0, 5);

        // when
        Slice<GetPostsDTO> result = postsRepositoryCustomImpl.findPostsSummaries(user.getId(), pageable);

        // then
        Assertions.assertThat(result.getContent()).hasSize(5);
        Assertions.assertThat(result.hasNext()).isTrue();
    }

    @Test
    void testFindPostsSummaries_likeAndCommentBoundary() {
        // given
        Users user = usersRepository.save(UserFixture.user("user3"));
        Posts post = postsRepository.save(PostFixture.post(user, "caption"));

        commentsRepository.save(CommentFixture.comment(user, post, "comment"));

        photosRepository.save(PhotoFixture.photo(post, "url", 1));

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Slice<GetPostsDTO> result = postsRepositoryCustomImpl.findPostsSummaries(user.getId(), pageable);

        // then
        GetPostsDTO dto = result.getContent().get(0);
        Assertions.assertThat(dto.getLiked()).isFalse();
        Assertions.assertThat(dto.getCommentCnt()).isEqualTo(1L);
        Assertions.assertThat(dto.getThumbnailUrl()).isEqualTo("url");
    }

    @DisplayName("게시글 2번째 페이지 확인")
    @Test
    void testFindPostsSummaries_secondPage() {
        // given
        Users user1 = usersRepository.save(UserFixture.user("user1"));
        Users user2 = usersRepository.save(UserFixture.user("user2"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user2, "test2"));
        Posts post3 = postsRepository.save(PostFixture.post(user1, "test3"));
        Posts post4 = postsRepository.save(PostFixture.post(user2, "test4"));
        Posts post5 = postsRepository.save(PostFixture.post(user2, "test5"));

        commentsRepository.save(CommentFixture.comment(user1, post1, "댓글1"));
        commentsRepository.save(CommentFixture.comment(user1, post1, "댓글2"));

        likesRepository.save(LikeFixture.like(user1, post1));
        likesRepository.save(LikeFixture.like(user2, post1));
        likesRepository.save(LikeFixture.like(user1, post2));
        likesRepository.save(LikeFixture.like(user2, post4));

        photosRepository.save(PhotoFixture.photo(post1, "url1", 1));
        photosRepository.save(PhotoFixture.photo(post1, "url1", 2));
        photosRepository.save(PhotoFixture.photo(post2, "url1", 1));
        photosRepository.save(PhotoFixture.photo(post2, "url1", 2));
        photosRepository.save(PhotoFixture.photo(post3, "url1", 1));
        photosRepository.save(PhotoFixture.photo(post3, "url1", 2));
        photosRepository.save(PhotoFixture.photo(post4, "url1", 1));

        Pageable pageable = PageRequest.of(1, 2);

        // when
        Slice<GetPostsDTO> result = postsRepositoryCustomImpl.findPostsSummaries(user1.getId(), pageable);

        // then
        Assertions.assertThat(result.getContent().get(0).getPostId()).isEqualTo(post3.getId());
        Assertions.assertThat(result.getContent()).hasSize(2);
        Assertions.assertThat(result.hasNext()).isTrue();
    }

}