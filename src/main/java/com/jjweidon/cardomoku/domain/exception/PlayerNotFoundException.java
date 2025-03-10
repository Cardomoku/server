package com.jjweidon.cardomoku.domain.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException() {
        super("Player가 존재하지 않습니다.");
    }

    public PlayerNotFoundException(String playerId) {
        super("ID: " + playerId + " 의 Player을 찾을 수 없습니다.");
    }
}
