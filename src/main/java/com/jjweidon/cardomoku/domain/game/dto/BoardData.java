package com.jjweidon.cardomoku.domain.game.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.game.entity.Board;
import com.jjweidon.cardomoku.domain.game.entity.enums.Card;
import com.jjweidon.cardomoku.global.entity.enums.Color;
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
    private Color status;

    public static BoardData from(Board board) {
        Card card = board.getCard();
        return BoardData.builder()
                .boardId(board.getId())
                .x(board.getX())
                .y(board.getY())
                .cardName(card.getSuit() + "_" + card.getRank() + "_" + card.getVersion() + ".svg")
                .status(board.getStatus())
                .build();
    }

}
