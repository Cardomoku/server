package com.jjweidon.cardomoku.domain.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.room.entity.Player;
import com.jjweidon.cardomoku.global.entity.enums.Color;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlayerData {
    private String playerId;
    private String nickname;
    private String image;
    private Color color;
    private Boolean isTurn;

    public static PlayerData from(Player player) {
        return PlayerData.builder()
                .playerId(player.getId())
                .nickname(player.getUser().getNickname())
                .image(player.getUser().getImage())
                .color(player.getColor())
                .isTurn(player.getIsTurn())
                .build();
    }
}
