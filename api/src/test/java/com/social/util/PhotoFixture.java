package com.social.util;

import com.social.domain.Photos;
import com.social.domain.Posts;

public class PhotoFixture {
    public static Photos photo(Posts post, String url, int sortOrder) {
        return Photos.builder()
                .post(post)
                .imageUrl(url)
                .sortOrder(sortOrder)
                .build();
    }
}
