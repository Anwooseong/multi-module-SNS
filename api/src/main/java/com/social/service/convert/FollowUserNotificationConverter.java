package com.social.service.convert;

import com.social.domain.Users;
import com.social.notification.domain.FollowNotification;
import com.social.repository.FollowsRepository;
import com.social.repository.UsersRepository;
import com.social.service.dto.ConvertedFollowNotification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FollowUserNotificationConverter {

    private final UsersRepository usersRepository;
    private final FollowsRepository followsRepository;

    public FollowUserNotificationConverter(UsersRepository usersRepository, FollowsRepository followsRepository) {
        this.usersRepository = usersRepository;
        this.followsRepository = followsRepository;
    }

    public ConvertedFollowNotification convert(FollowNotification notification) {
        Users toUser = usersRepository.findById(notification.getToUserId())
                .orElseThrow(() -> new UsernameNotFoundException(notification.getToUserId() + " -> 데이터베이스에서 찾을 수 없습니다."));

        Users fromUser = usersRepository.findById(notification.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(notification.getUserId() + " -> 데이터베이스에서 찾을 수 없습니다."));

        boolean isFollowing = followsRepository.existsByFromUserAndToUser(fromUser, toUser);

        return new ConvertedFollowNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                toUser.getUsername(),
                toUser.getProfileImageUrl(),
                isFollowing
        );
    }
}
