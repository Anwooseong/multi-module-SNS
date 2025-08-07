package com.social.service;

import com.social.IntegrationTestSupport;
import com.social.controller.request.CommentRequest;
import com.social.domain.Comments;
import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.event.CommentEvent;
import com.social.repository.CommentsRepository;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import com.social.util.CommentFixture;
import com.social.util.PostFixture;
import com.social.util.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class CommentsServiceTest extends IntegrationTestSupport {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @MockBean
    private KafkaTemplate<String, CommentEvent> kafkaTemplate;

    @AfterEach
    void tearDown() {
        System.out.println("-------------tearDown Start----------------");
        commentsRepository.deleteAllInBatch();
        postsRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();
        System.out.println("-------------tearDown Finish----------------");
    }

    @DisplayName("게시글 등록 테스트")
    @Test
    void createComment() {
        // given
        Users user1 = usersRepository.save(UserFixture.user("user1"));
        Users user2 = usersRepository.save(UserFixture.user("user2"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user2, "test2"));

        CommentRequest request = CommentRequest.builder()
                .content("testCaption")
                .build();

        // when
        Comments comment = commentsService.createComment(user1.getId(), post1.getId(), request);

        // then
        Assertions.assertThat(comment.getContent()).isEqualTo("testCaption");
        Assertions.assertThat(comment.getPost().getId()).isEqualTo(post1.getId());
        verify(kafkaTemplate).send(any(String.class), any(CommentEvent.class));
    }

    @DisplayName("게시글 수정 테스트")
    @Test
    void modifyComment() {
        // given
        Users user1 = usersRepository.save(UserFixture.user("user1"));
        Users user2 = usersRepository.save(UserFixture.user("user2"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user2, "test2"));

        Comments comment1 = commentsRepository.save(CommentFixture.comment(user1, post1, "댓글1"));
        Comments comment2 = commentsRepository.save(CommentFixture.comment(user1, post1, "댓글2"));

        CommentRequest request = CommentRequest.builder()
                .content("testCaption")
                .build();

        // when
        Comments comment = commentsService.modifyComment(user1.getId(), post1.getId(), comment1.getId(), request);

        // then
        Assertions.assertThat(comment.getContent()).isEqualTo("testCaption");
        Assertions.assertThat(comment.getPost().getId()).isEqualTo(post1.getId());
        verify(kafkaTemplate).send(any(String.class), any(CommentEvent.class));
    }
}