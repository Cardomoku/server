package com.jjweidon.cardomoku.domain.game.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GameStatus {
    NOT_STARTED("시작 전"),
    IN_PROGRESS("진행 중"),
    TERMINATED("종료");

    private final String value;

    @JsonCreator
    public static GameStatus fromValue(String value) {
        return Arrays.stream(values())
                .filter(constant -> constant.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}
