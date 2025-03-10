package com.jjweidon.cardomoku.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.entity.Player;
import com.jjweidon.cardomoku.domain.entity.enums.Team;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlayerData {
    private String playerId;
    private String nickname;
    private String image;
    private Team team;
    private Boolean isTurn;

    public static PlayerData from(Player player) {
        return PlayerData.builder()
                .playerId(player.getId())
                .nickname(player.getUser().getNickname())
                .image(player.getUser().getImage())
                .team(player.getTeam())
                .isTurn(player.isTurn())
                .build();
    }
}
