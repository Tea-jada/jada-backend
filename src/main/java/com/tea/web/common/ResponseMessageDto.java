package com.tea.web.common;

import lombok.Getter;

@Getter
public class ResponseMessageDto {

  private int status;
  private String message;

  public ResponseMessageDto(ResponseStatus status) {
    this.status = status.getHttpStatus().value();
    this.message = status.getMessage();
  }

  // 아래 생성자를 추가
  // TODO: http status 값 ErrorType에서 선언한 값으로 반환하도록 수정
  public ResponseMessageDto(String message) {
    this.status = 400; // 또는 적절한 기본값 (예: 400 Bad Request)
    this.message = message;
  }

  // 사용 예시
  // return ResponseEntity.ok(new
  // ResponseMessageDto(ResponseStatus.COMMENT_DELETE_SUCCESS));
}