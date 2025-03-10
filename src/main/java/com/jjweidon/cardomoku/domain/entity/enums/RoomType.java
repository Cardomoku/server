package com.jjweidon.cardomoku.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RoomType {
    PVP("1vs1", 2),
    PVPVP("1vs1vs1", 3),
    TVT("2vs2", 4);

    private final String value;
    private final int capacity;

    @JsonCreator
    public static RoomType fromValue(String value) {
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
