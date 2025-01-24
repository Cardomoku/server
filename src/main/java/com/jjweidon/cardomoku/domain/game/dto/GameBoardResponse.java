package com.jjweidon.cardomoku.domain.game.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.global.dto.Response;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GameBoardResponse implements Response {
    List<BoardData> boards;

    public static GameBoardResponse from(List<BoardData> boards) {
        return GameBoardResponse.builder()
                .boards(boards)
                .build();
    }
}
