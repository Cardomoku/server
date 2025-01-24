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
public class MyCardsResponse implements Response {
    private List<MyCardData> myCards;

    public static MyCardsResponse from(List<MyCardData> myCards) {
        return MyCardsResponse.builder()
                .myCards(myCards)
                .build();
    }
}
