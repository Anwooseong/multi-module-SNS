package com.social.controller;

import com.social.ControllerTestSupport;
import com.social.controller.request.CreatePostsRequest;
import com.social.domain.Posts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostsControllerTest extends ControllerTestSupport {

    @DisplayName("게시글 등록 성공")
    @Test
    @WithMockUser
    void createPost_success() throws Exception {
        // given
        List<String> imageUrls = List.of("image1.jpg", "image2.jpg", "image3.jpg");
        String caption = "게시글 테스트 설명글";
        CreatePostsRequest request = CreatePostsRequest.builder()
                .caption(caption)
                .imageUrls(imageUrls)
                .build();

        Long postId = 1L;

        Posts mockPost = Posts.builder()
                .id(postId)
                .caption(caption)
                .build();
        // when
        BDDMockito.given(postsService.createPost(any(CreatePostsRequest.class)))
                .willReturn(mockPost);

        // then
        mockMvc.perform(post(
                        "/api/v1/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(postId));
    }

}