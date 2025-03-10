//package com.jjweidon.cardomoku.domain.dto;
//
//import com.fasterxml.jackson.databind.PropertyNamingStrategies;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import com.jjweidon.cardomoku.domain.entity.Game;
//import com.jjweidon.cardomoku.global.dto.Response;
//import com.jjweidon.cardomoku.domain.entity.enums.Team;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.List;
//
//@Getter
//@Builder
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//public class GameResultResponse implements Response {
//    private Team winningTeam;
//    private List<ContributionData> contributions;
//
//    public static GameResultResponse from(Game game, List<ContributionData> contributions) {
//        return GameResultResponse.builder()
//                .winningTeam(game.getWinningTeam())
//                .contributions(contributions)
//                .build();
//    }
//}
