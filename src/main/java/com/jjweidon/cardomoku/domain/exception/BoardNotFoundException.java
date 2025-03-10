package com.jjweidon.cardomoku.domain.exception;

public class BoardNotFoundException extends RuntimeException {
  public BoardNotFoundException() {
    super("Board가 존재하지 않습니다.");
  }
}
