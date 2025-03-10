package com.jjweidon.cardomoku.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GameMessage {
    private String type;    // CARD_USED, TURN_CHANGED, GAME_OVER 등
    private String gameId;
    private String playerId;
    private Object data;    // 실제 전송할 데이터
} 