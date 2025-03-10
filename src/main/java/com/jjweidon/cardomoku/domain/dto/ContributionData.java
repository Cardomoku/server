package com.jjweidon.cardomoku.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.entity.Player;
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
                .fourCreated(player.getFourCount())
                .bingoCreated(player.getBingoCount())
                .jUsed(player.getJCount())
                .build();
    }
}
