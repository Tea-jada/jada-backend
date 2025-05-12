package com.tea.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

  SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다."),
  GET_USER_SUCCESS(HttpStatus.OK, "유저 조회에 성공했습니다.")
  ;

  private final HttpStatus httpStatus;
  private final String message;
}