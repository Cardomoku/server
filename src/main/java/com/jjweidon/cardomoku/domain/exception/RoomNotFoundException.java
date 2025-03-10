package com.jjweidon.cardomoku.domain.exception;

public class RoomNotFoundException extends RuntimeException {
  public RoomNotFoundException() {
    super("Room이 존재하지 않습니다.");
  }

  public RoomNotFoundException(String code) {
    super("Code: " + code + " 의 Room을 찾을 수 없습니다.");
  }
}
