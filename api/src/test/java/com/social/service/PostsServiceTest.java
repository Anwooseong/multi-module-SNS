package com.social.service;

import com.social.IntegrationTestSupport;
import com.social.controller.request.CreatePostsRequest;
import com.social.domain.Photos;
import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.repository.PhotosRepository;
import com.social.repository.PostsRepository;
import com.social.repository.UsersRepository;
import com.social.util.SecurityUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.mockStatic;

class PostsServiceTest extends IntegrationTestSupport {

    @Autowired
    private PostsService postsService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PhotosRepository photosRepository;

    @Autowired
    private PostsRepository postsRepository;

    private MockedStatic<SecurityUtil> mockSecurityUtil;

    @BeforeEach
    void setUp() {
        mockSecurityUtil = mockStatic(SecurityUtil.class);
    }

    @AfterEach
    void tearDown() {
        photosRepository.deleteAllInBatch();
        postsRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();

        mockSecurityUtil.close();
    }

    @DisplayName("게시글 등록 성공")
    @Test
    void createPost_success() {
        // given
        String caption = "테스트 게스글 설명";
        List<String> imageUrls = List.of("image1.jpg", "image2.jpg", "image3.jpg");

        Users saveTestUser = usersRepository.save(Users.builder().build());
        Long currentId = saveTestUser.getId();

        CreatePostsRequest request = CreatePostsRequest.builder()
                .caption(caption)
                .imageUrls(imageUrls)
                .build();

        mockSecurityUtil.when(SecurityUtil::getCurrentUserId).thenReturn(currentId);

        // when
        Posts savePost = postsService.createPost(request);
        Posts findPost = postsRepository.findWithPhotosByIdOrderBySortOrderAsc(savePost.getId()).get();
        List<Photos> findPhotos = findPost.getPhotos();

        // then
        Assertions.assertThat(findPost.getCaption()).isEqualTo(savePost.getCaption());
        Assertions.assertThat(findPhotos.size()).isEqualTo(3);
        for (int i = 0; i < findPhotos.size(); i++) {
            Photos photo = findPhotos.get(i);
            Assertions.assertThat(photo.getImageUrl()).isEqualTo(imageUrls.get(i));
            Assertions.assertThat(photo.getSortOrder()).isEqualTo(i);
        }
    }

}