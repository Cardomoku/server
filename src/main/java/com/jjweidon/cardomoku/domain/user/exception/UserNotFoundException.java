package com.jjweidon.cardomoku.domain.user.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String userId) {
    super("Id: " + userId + " 의 User를 찾을 수 없습니다.");
  }
}
