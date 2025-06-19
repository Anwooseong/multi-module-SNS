package com.social.client;


import com.social.notification.domain.Post;

public interface PostClient {
    Post getPost(Long id);
}
