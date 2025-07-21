package com.social.service.convert;

import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.notification.domain.CommentNotification;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import com.social.service.dto.ConvertedCommentNotification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentUserNotificationConverter {

    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    public CommentUserNotificationConverter(UsersRepository usersRepository, PostsRepository postsRepository) {
        this.usersRepository = usersRepository;
        this.postsRepository = postsRepository;
    }

    public ConvertedCommentNotification convert(CommentNotification notification) {
        Users user = usersRepository.findById(notification.getWriterId())
                .orElseThrow(() -> new UsernameNotFoundException(notification.getWriterId() + " -> 데이터베이스에서 찾을 수 없습니다."));

        Posts post = postsRepository.findWithPhotosByIdOrderBySortOrderAsc(notification.getPostId())
                .orElseThrow(() -> new NoSuchElementException(notification.getPostId() + "를 찾을 수 없습니다."));

        return new ConvertedCommentNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getUsername(),
                user.getProfileImageUrl(),
                notification.getComment(),
                post.getPhotos().get(0).getImageUrl()
        );
    }
}
