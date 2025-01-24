package com.jjweidon.cardomoku.domain.game.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.room.entity.Player;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ContributionData {
    private String playerId;
    private String image;
    private String nickname;
    private int fourCreated;
    private int bingoCreated;
    private int jUsed;

    public static ContributionData from(Player player) {
        return ContributionData.builder()
                .playerId(player.getId())
                .image(player.getUser().getImage())
                .nickname(player.getUser().getNickname())
                .fourCreated(player.getFourCreated())
                .bingoCreated(player.getBingoCreated())
                .jUsed(player.getJUsed())
                .build();
    }
}
