package com.jjweidon.cardomoku.domain.room.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException() {
        super("Player가 존재하지 않습니다.");
    }
}
