package com.jjweidon.cardomoku.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.entity.GameBoard;
import com.jjweidon.cardomoku.domain.entity.Card;
import com.jjweidon.cardomoku.domain.entity.enums.Team;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BoardData {
    private String boardId;
    private int x;
    private int y;
    private String cardName;
    private Team status;

    public static BoardData from(GameBoard gameBoard) {
        Card card = gameBoard.getCard();
        return BoardData.builder()
                .boardId(gameBoard.getId())
                .x(gameBoard.getX())
                .y(gameBoard.getY())
                .cardName(card.getSuit() + "_" + card.getNumber() + "_" + card.getVersion() + ".svg")
                .status(gameBoard.getStatus())
                .build();
    }

}
