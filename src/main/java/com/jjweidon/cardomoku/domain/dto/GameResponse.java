//package com.jjweidon.cardomoku.domain.dto;
//
//import com.fasterxml.jackson.databind.PropertyNamingStrategies;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import com.jjweidon.cardomoku.domain.entity.Game;
//import com.jjweidon.cardomoku.global.dto.Response;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Builder
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//public class GameResponse implements Response {
//    private String gameId;
//    private LocalDateTime startedAt;
//
//    public static GameResponse from(Game game) {
//        return GameResponse.builder()
//                .gameId(game.getId())
//                .startedAt(game.getCreatedAt())
//                .build();
//    }
//}
