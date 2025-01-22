package com.jjweidon.cardomoku.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateUserResponse {
    private String userId;
    private String nickname;
    private LocalDateTime updatedAt;

    public static UpdateUserResponse from (User user) {
        return UpdateUserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
