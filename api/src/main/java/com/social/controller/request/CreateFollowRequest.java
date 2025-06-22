package com.social.controller.request;

import com.social.domain.Follows;
import com.social.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateFollowRequest {

    @NotNull
    @Schema(type = "Long", description = "팔로우할 유저 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long targetId;

    @Builder
    public CreateFollowRequest(Long targetId) {
        this.targetId = targetId;
    }

    public Follows toFollowsEntity(Users fromUser, Users toUser) {
        return Follows.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
    }
}
