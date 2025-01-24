package com.jjweidon.cardomoku.domain.game.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.room.dto.PlayerData;
import com.jjweidon.cardomoku.global.dto.Response;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlayersResponse implements Response {
    private List<PlayerData> players;

    public static PlayersResponse from(List<PlayerData> players) {
        return PlayersResponse.builder()
                .players(players)
                .build();
    }
}
