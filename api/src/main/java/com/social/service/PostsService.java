package com.social.service;

import com.social.controller.request.CreatePostsRequest;
import com.social.controller.request.UpdatePostsRequest;
import com.social.controller.response.SliceResponse;
import com.social.domain.Photos;
import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.repository.PhotosRepository;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import com.social.repository.querydslDTO.GetPostsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public Posts createPost(CreatePostsRequest request) {

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

        return savePost;
    }

    @Transactional(readOnly = true)
    public SliceResponse<GetPostsDTO> getFeed(Long userId, Pageable pageable) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 ID가 존재하지 않습니다."));
        return SliceResponse.of(postsRepository.findPostsSummaries(user.getId(), pageable));
    }

    @Transactional
    public Posts updatePost(Long userId, Long postId, UpdatePostsRequest request) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 ID가 존재하지 않습니다."));

        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("선택한 게시글은 존재하지 않습니다."));

        return posts.update(request.toPostsEntity(user), request.getImageUrls());
    }
}
