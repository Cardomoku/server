//package com.jjweidon.cardomoku.domain.dto;
//
//import com.fasterxml.jackson.databind.PropertyNamingStrategies;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import com.jjweidon.cardomoku.domain.entity.PlayerCard;
//import com.jjweidon.cardomoku.domain.entity.enums.Card;
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//@Builder
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//public class MyCardData {
//    private String playerCardId;
//    private String cardName;
//
//    public static MyCardData from(PlayerCard playerCard) {
//        Card card = playerCard.getCard();
//        return MyCardData.builder()
//                .playerCardId(playerCard.getId())
//                .cardName(card.getSuit() + "_" + card.getRank() + "_" + card.getVersion() + ".svg")
//                .build();
//    }
//}
