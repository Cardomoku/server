//package com.jjweidon.cardomoku.domain.dto;
//
//import com.fasterxml.jackson.databind.PropertyNamingStrategies;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import com.jjweidon.cardomoku.domain.entity.Game;
//import com.jjweidon.cardomoku.domain.entity.enums.GameStatus;
//import com.jjweidon.cardomoku.global.dto.Response;
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//@Builder
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//public class GameStatusResponse implements Response {
//    private GameStatus status;
//
//    public static GameStatusResponse from(Game game) {
//        return GameStatusResponse.builder()
//                .status(game.getRoom().getStatus())
//                .build();
//    }
//}
