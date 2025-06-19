package com.social.service;

import com.social.controller.request.LikesRequest;
import com.social.domain.Likes;
import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.event.LikeEvent;
import com.social.event.LikeEventType;
import com.social.repository.LikesRepository;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.social.util.SecurityUtil.getCurrentUserId;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final KafkaTemplate<String, LikeEvent> kafkaTemplate;

    @Transactional
    public Long like(LikesRequest likesRequest) {
        Users user = usersRepository.findById(getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("유저 ID가 존재하지 않습니다."));

        Long postId = likesRequest.getPostId();
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 ID가 존재하지 않습니다."));

        boolean isLiked = likesRepository.existsByUserAndPost(user, post);

        Long likeId;
        LikeEventType type;
        if (isLiked) {
            likeId = likesRepository.deleteByUserAndPost(user, post);
            type = LikeEventType.REMOVE;
        } else {
            Likes likeToEntity = likesRequest.toLikesEntity(user, post);
            Likes likes = likesRepository.save(likeToEntity);
            likeId = likes.getId();
            type = LikeEventType.ADD;
        }

        String kafkaTopic = "like";
        LikeEvent likeEvent = new LikeEvent(type, post.getId(), user.getId(), Instant.now());
        kafkaTemplate.send(kafkaTopic, likeEvent);
        log.info("Kafka LikeEvent published: {}", likeEvent);


        return likeId;
    }
}
