package com.jjweidon.cardomoku.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserProfileResponse {
    private String userId;
    private String email;
    private String nickname;
    private String image;
    private int coin;
    private int win;
    private int lose;
    private int draw;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .image(user.getImage())
                .coin(user.getCoin())
                .win(user.getWin())
                .lose(user.getLose())
                .draw(user.getDraw())
                .build();
    }
}
