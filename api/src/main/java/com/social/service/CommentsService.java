package com.social.service;

import com.social.controller.request.CreateCommentsRequest;
import com.social.domain.Comments;
import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.event.CommentEvent;
import com.social.event.CommentEventType;
import com.social.repository.CommentsRepository;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final KafkaTemplate<String, CommentEvent> kafkaTemplate;

    public Comments createComment(Long userId, Long postId, CreateCommentsRequest request) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 ID가 존재하지 않습니다."));

        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 ID가 존재하지 않습니다."));

        Comments commentToEntity = request.toCommentsEntity(user, post);

        Comments saveComment = commentsRepository.save(commentToEntity);

        String kafkaTopic = "comment";
        CommentEvent commentEvent = new CommentEvent(CommentEventType.ADD, post.getId(), user.getId(), saveComment.getId());
        kafkaTemplate.send(kafkaTopic, commentEvent);
        return saveComment;
    }
}
