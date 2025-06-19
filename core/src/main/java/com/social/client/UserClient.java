package com.social.client;


import com.social.notification.domain.User;

public interface UserClient {

    User getUser(long id);

    Boolean getIsFollowing(long followerId, long followedId);
}
