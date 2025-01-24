package com.jjweidon.cardomoku.domain.game.exception;

public class BoardNotFoundException extends RuntimeException {
  public BoardNotFoundException() {
    super("Board가 존재하지 않습니다.");
  }
}
