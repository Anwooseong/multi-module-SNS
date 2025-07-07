package com.social.repository;

import com.social.domain.Follows;
import com.social.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowsRepository extends JpaRepository<Follows, Long> {

    boolean existsByFromUserAndToUser(Users fromUser, Users toUser);

    Optional<Follows> findByFromUserAndToUser(Users fromUser, Users toUser);
}
