package com.social.service;

import com.social.IntegrationTestSupport;
import com.social.controller.request.CommentRequest;
import com.social.controller.response.SliceResponse;
import com.social.domain.Comments;
import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.event.CommentEvent;
import com.social.repository.CommentsRepository;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import com.social.repository.querydslDTO.GetCommentDTO;
import com.social.util.CommentFixture;
import com.social.util.PostFixture;
import com.social.util.UserFixture;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

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

    @DisplayName("게시글 댓글 수정 테스트")
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

    @DisplayName("게시글 댓글 삭제 테스트")
    @Test
    void deleteComment_success() {
        // given
        Users user1 = usersRepository.save(UserFixture.user("user1"));
        Users user2 = usersRepository.save(UserFixture.user("user2"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user2, "test2"));

        Comments comment1 = commentsRepository.save(CommentFixture.comment(user1, post1, "댓글1"));
        Comments comment2 = commentsRepository.save(CommentFixture.comment(user1, post1, "댓글2"));

        // when
        commentsService.deleteComment(user1.getId(), post1.getId(), comment1.getId());
        Optional<Comments> deletedComment = commentsRepository.findById(comment1.getId());

        // then
        Assertions.assertThat(deletedComment).isEmpty();
    }

    @DisplayName("게시글 댓글 작성자가 아닐 때 삭제 실패 테스트")
    @Test
    void deleteComment_failed_notFound() {
        // given
        Users user1 = usersRepository.save(UserFixture.user("user1"));
        Users user2 = usersRepository.save(UserFixture.user("user2"));
        Users user3 = usersRepository.save(UserFixture.user("user3"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user2, "test2"));

        Comments comment1 = commentsRepository.save(CommentFixture.comment(user1, post1, "댓글1"));
        Comments comment2 = commentsRepository.save(CommentFixture.comment(user1, post1, "댓글2"));

        // when
        // then
        Assertions.assertThatThrownBy(() -> commentsService.deleteComment(user3.getId(), post1.getId(), comment1.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("조건에 맞는 댓글이 존재하지 않습니다.");
    }

    @DisplayName("게시글 댓글 조회 첫번째 페이지")
    @Test
    void getComments_firstPage() {
        // given
        Users user1 = usersRepository.save(UserFixture.user("user1"));
        Users user2 = usersRepository.save(UserFixture.user("user2"));
        Users user3 = usersRepository.save(UserFixture.user("user3"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user2, "test2"));

        Comments comment1 = commentsRepository.save(CommentFixture.comment(user1, post1, "댓글1"));
        Comments comment2 = commentsRepository.save(CommentFixture.comment(user2, post1, "댓글2"));
        Comments comment3 = commentsRepository.save(CommentFixture.comment(user3, post1, "댓글3"));
        Comments comment4 = commentsRepository.save(CommentFixture.comment(user3, post1, "댓글4"));
        Comments comment5 = commentsRepository.save(CommentFixture.comment(user3, post1, "댓글5"));
        Comments comment6 = commentsRepository.save(CommentFixture.comment(user3, post1, "댓글6"));

        // when
        SliceResponse<GetCommentDTO> result = commentsService.getComments(post1.getId(), PageRequest.of(0, 5));

        // then
        Assertions.assertThat(result.getCurrentPage()).isEqualTo(0);
        Assertions.assertThat(result.getPageSize()).isEqualTo(5);
        Assertions.assertThat(result.isHasNext()).isTrue();
        Assertions.assertThat(result.getData()).hasSize(5);

        Assertions.assertThat(result.getData())
                .extracting("userId", "username", "commentId", "content")
                .containsExactly(
                        Tuple.tuple(user3.getId(), user3.getUsername(), comment6.getId(), "댓글6"),
                        Tuple.tuple(user3.getId(), user3.getUsername(), comment5.getId(), "댓글5"),
                        Tuple.tuple(user3.getId(), user3.getUsername(), comment4.getId(), "댓글4"),
                        Tuple.tuple(user3.getId(), user3.getUsername(), comment3.getId(), "댓글3"),
                        Tuple.tuple(user2.getId(), user2.getUsername(), comment2.getId(), "댓글2")
                );

        SliceResponse<GetCommentDTO> resultPost2 = commentsService.getComments(post2.getId(), PageRequest.of(0, 5));
        Assertions.assertThat(resultPost2.getData()).isEmpty();
    }

    @DisplayName("게시글 댓글 조회 두번째 페이지")
    @Test
    void getComments_secondPage() {
        // given
        Users user1 = usersRepository.save(UserFixture.user("user1"));
        Users user2 = usersRepository.save(UserFixture.user("user2"));
        Users user3 = usersRepository.save(UserFixture.user("user3"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user2, "test2"));

        Comments comment1 = commentsRepository.save(CommentFixture.comment(user1, post1, "댓글1"));
        Comments comment2 = commentsRepository.save(CommentFixture.comment(user2, post1, "댓글2"));
        Comments comment3 = commentsRepository.save(CommentFixture.comment(user3, post1, "댓글3"));
        Comments comment4 = commentsRepository.save(CommentFixture.comment(user3, post1, "댓글4"));
        Comments comment5 = commentsRepository.save(CommentFixture.comment(user3, post1, "댓글5"));
        Comments comment6 = commentsRepository.save(CommentFixture.comment(user3, post1, "댓글6"));

        // when
        SliceResponse<GetCommentDTO> result = commentsService.getComments(post1.getId(), PageRequest.of(1, 5));

        // then
        Assertions.assertThat(result.getCurrentPage()).isEqualTo(1);
        Assertions.assertThat(result.getPageSize()).isEqualTo(5);
        Assertions.assertThat(result.isHasNext()).isFalse();
        Assertions.assertThat(result.getData()).hasSize(1);

        Assertions.assertThat(result.getData())
                .extracting("userId", "username", "commentId", "content")
                .containsExactly(
                        Tuple.tuple(user1.getId(), user1.getUsername(), comment1.getId(), "댓글1")
                );

        SliceResponse<GetCommentDTO> resultPost2 = commentsService.getComments(post2.getId(), PageRequest.of(0, 5));
        Assertions.assertThat(resultPost2.getData()).isEmpty();
    }
}