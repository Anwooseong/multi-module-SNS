package com.social.service;

import com.social.controller.request.CreateFollowRequest;
import com.social.domain.Follows;
import com.social.domain.Users;
import com.social.event.FollowEvent;
import com.social.event.FollowEventType;
import com.social.repository.FollowsRepository;
import com.social.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.social.util.SecurityUtil.getCurrentUserId;

@Service
@RequiredArgsConstructor
public class FollowsService {

    private final UsersRepository usersRepository;
    private final FollowsRepository followsRepository;
    private final KafkaTemplate<String, FollowEvent> kafkaTemplate;

    @Transactional
    public Long createFollow(CreateFollowRequest request) {

        Users fromUser = usersRepository.findById(getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("유저 ID가 존재하지 않습니다."));

        Users toUser = usersRepository.findById(request.getTargetId())
                .orElseThrow(() -> new RuntimeException("유저 ID가 존재하지 않습니다."));

        if (fromUser.getId().equals(toUser.getId())){
            throw new IllegalArgumentException("팔로우할 ID가 유저의 ID와 같습니다.");
        }

        boolean existsFollow = followsRepository.existsByFromUserAndToUser(fromUser, toUser);
        if (existsFollow) {
            throw new IllegalArgumentException("이미 팔로우한 유저입니다.");
        }
        Follows followsToEntity = request.toFollowsEntity(fromUser, toUser);
        Follows saveFollow = followsRepository.save(followsToEntity);

        String kafkaTopic = "follow";
        FollowEvent followEvent = new FollowEvent(FollowEventType.ADD, fromUser.getId(), toUser.getId(), Instant.now());
        kafkaTemplate.send(kafkaTopic, followEvent);
        return saveFollow.getId();
    }
}
