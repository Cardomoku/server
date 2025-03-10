package com.jjweidon.cardomoku.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GameStatus {
    NOT_STARTED,
    IN_PROGRESS,
    TERMINATED;
}
