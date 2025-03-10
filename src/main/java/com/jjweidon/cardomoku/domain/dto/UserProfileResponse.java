package com.jjweidon.cardomoku.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.entity.User;
import com.jjweidon.cardomoku.global.dto.Response;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserProfileResponse implements Response {
    private String userId;
    private String email;
    private String nickname;
    private String image;
    private long coin;
    private long win;
    private long lose;
    private long draw;

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
