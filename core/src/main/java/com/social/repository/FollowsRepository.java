package com.social.repository;

import com.social.domain.Follows;
import com.social.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowsRepository extends JpaRepository<Follows, Long> {

    boolean existsByFromUserAndToUser(Users fromUser, Users toUser);
}
