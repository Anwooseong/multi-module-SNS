package com.social.service;

import com.social.IntegrationTestSupport;
import com.social.controller.request.CreatePostsRequest;
import com.social.controller.response.SliceResponse;
import com.social.domain.Photos;
import com.social.domain.Posts;
import com.social.domain.Users;
import com.social.repository.*;
import com.social.repository.querydslDTO.GetPostsDTO;
import com.social.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private LikesRepository likesRepository;

    private MockedStatic<SecurityUtil> mockSecurityUtil;

    @BeforeEach
    void setUp() {
        mockSecurityUtil = mockStatic(SecurityUtil.class);
    }

    @AfterEach
    void tearDown() {
        photosRepository.deleteAllInBatch();
        commentsRepository.deleteAllInBatch();
        likesRepository.deleteAllInBatch();
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

    @DisplayName("게시글 조회 성공 2페이지 중 1페이지")
    @Test
    void getFeed_firstPage() throws NoSuchFieldException, IllegalAccessException {
        // given
        int pageNumber = 0;
        int pageSize = 3;

        Users user1 = usersRepository.save(UserFixture.user("user1"));
        Users user2 = usersRepository.save(UserFixture.user("user2"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user2, "test2"));
        Posts post3 = postsRepository.save(PostFixture.post(user1, "test3"));
        Posts post4 = postsRepository.save(PostFixture.post(user2, "test4"));
        Posts post5 = postsRepository.save(PostFixture.post(user2, "test5"));

        commentsRepository.save(CommentFixture.comment(user1, post1, "댓글1"));
        commentsRepository.save(CommentFixture.comment(user1, post1, "댓글2"));

        likesRepository.save(LikeFixture.like(user1, post1));
        likesRepository.save(LikeFixture.like(user2, post1));
        likesRepository.save(LikeFixture.like(user1, post2));
        likesRepository.save(LikeFixture.like(user2, post4));

        photosRepository.save(PhotoFixture.photo(post1, "url1", 1));
        photosRepository.save(PhotoFixture.photo(post1, "url2", 2));
        photosRepository.save(PhotoFixture.photo(post2, "url3", 1));
        photosRepository.save(PhotoFixture.photo(post2, "url4", 2));
        photosRepository.save(PhotoFixture.photo(post3, "url5", 1));
        photosRepository.save(PhotoFixture.photo(post3, "url6", 2));
        photosRepository.save(PhotoFixture.photo(post4, "url7", 1));
        photosRepository.save(PhotoFixture.photo(post5, "url8", 1));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // when
        SliceResponse<GetPostsDTO> result = postsService.getFeed(user1.getId(), pageable);

        // then
        Assertions.assertThat(result.getCurrentPage()).isEqualTo(pageNumber);
        Assertions.assertThat(result.getPageSize()).isEqualTo(pageSize);
        Assertions.assertThat(result.isHasNext()).isTrue();
        Assertions.assertThat(result.getData().size()).isEqualTo(pageSize);
    }

    @DisplayName("게시글 조회 성공 2페이지 중 2페이지")
    @Test
    void getFeed_secondPage() throws NoSuchFieldException, IllegalAccessException {
        // given
        int pageNumber = 1;
        int pageSize = 3;

        Users user1 = usersRepository.save(UserFixture.user("user1"));
        Users user2 = usersRepository.save(UserFixture.user("user2"));

        Posts post1 = postsRepository.save(PostFixture.post(user1, "test1"));
        Posts post2 = postsRepository.save(PostFixture.post(user2, "test2"));
        Posts post3 = postsRepository.save(PostFixture.post(user1, "test3"));
        Posts post4 = postsRepository.save(PostFixture.post(user2, "test4"));
        Posts post5 = postsRepository.save(PostFixture.post(user2, "test5"));

        commentsRepository.save(CommentFixture.comment(user1, post1, "댓글1"));
        commentsRepository.save(CommentFixture.comment(user1, post1, "댓글2"));

        likesRepository.save(LikeFixture.like(user1, post1));
        likesRepository.save(LikeFixture.like(user2, post1));
        likesRepository.save(LikeFixture.like(user1, post2));
        likesRepository.save(LikeFixture.like(user2, post4));

        photosRepository.save(PhotoFixture.photo(post1, "url1", 1));
        photosRepository.save(PhotoFixture.photo(post1, "url2", 2));
        photosRepository.save(PhotoFixture.photo(post2, "url3", 1));
        photosRepository.save(PhotoFixture.photo(post2, "url4", 2));
        photosRepository.save(PhotoFixture.photo(post3, "url5", 1));
        photosRepository.save(PhotoFixture.photo(post3, "url6", 2));
        photosRepository.save(PhotoFixture.photo(post4, "url7", 1));
        photosRepository.save(PhotoFixture.photo(post5, "url8", 1));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // when
        SliceResponse<GetPostsDTO> result = postsService.getFeed(user1.getId(), pageable);

        // then
        Assertions.assertThat(result.getCurrentPage()).isEqualTo(pageNumber);
        Assertions.assertThat(result.getPageSize()).isEqualTo(pageSize);
        Assertions.assertThat(result.isHasNext()).isFalse();
        Assertions.assertThat(result.getData().size()).isEqualTo(2);
    }

}