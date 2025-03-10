package com.jjweidon.cardomoku.domain.exception;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException() {
        super("Game이 존재하지 않습니다.");
    }

    public GameNotFoundException(String gameId) {
        super("ID: " + gameId + " 의 Game을 찾을 수 없습니다.");
    }
}
