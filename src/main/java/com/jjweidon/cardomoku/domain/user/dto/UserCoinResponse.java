package com.jjweidon.cardomoku.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserCoinResponse {
    private String userId;
    private int coin;

    public static UserCoinResponse from(User user) {
        return UserCoinResponse.builder()
                .userId(user.getId())
                .coin(user.getCoin())
                .build();
    }
}