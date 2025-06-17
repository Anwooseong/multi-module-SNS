package com.social.service;

import com.social.controller.request.CreatePostsRequest;
import com.social.domain.Photos;
import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.repository.PhotosRepository;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.social.util.SecurityUtil.getCurrentUserId;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final PhotosRepository photosRepository;

    @Transactional
    public Long createPost(CreatePostsRequest request) {

        Users user = usersRepository.findById(getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("유저 ID가 존재하지 않습니다."));

        Posts savePost = postsRepository.save(request.toPostsEntity(user));

        List<Photos> photosToEntity = IntStream.range(0, request.getImageUrls().size()) // 0부터 imageUrls.size()까지의 인덱스 스트림 생성
                .mapToObj(index -> {
                    String imageUrl = request.getImageUrls().get(index);
                    return Photos.builder()
                            .post(savePost)
                            .imageUrl(imageUrl)
                            .sortOrder(index)
                            .build();
                })
                .toList();


        photosRepository.saveAll(photosToEntity);

        return savePost.getId();
    }
}
