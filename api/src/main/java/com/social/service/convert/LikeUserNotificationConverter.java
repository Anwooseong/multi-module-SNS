package com.social.service.convert;

import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.notification.domain.LikeNotification;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import com.social.service.dto.ConvertedLikeNotification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class LikeUserNotificationConverter {

    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    public LikeUserNotificationConverter(UsersRepository usersRepository, PostsRepository postsRepository) {
        this.usersRepository = usersRepository;
        this.postsRepository = postsRepository;
    }

    public ConvertedLikeNotification convert(LikeNotification notification) {
        Long lastUserId = notification.getLikerIds().getLast();
        Users user = usersRepository.findById(lastUserId)
                .orElseThrow(() -> new UsernameNotFoundException(lastUserId + " -> 데이터베이스에서 찾을 수 없습니다."));

        Posts post = postsRepository.findWithPhotosByIdOrderBySortOrderAsc(notification.getPostId())
                .orElseThrow(() -> new NoSuchElementException(notification.getPostId() + "를 찾을 수 없습니다."));

        return new ConvertedLikeNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getUsername(),
                user.getProfileImageUrl(),
                notification.getLikerIds().size(),
                post.getPhotos().get(0).getImageUrl()
        );
    }
}
