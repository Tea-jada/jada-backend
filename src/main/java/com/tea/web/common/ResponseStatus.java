package com.tea.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

  SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다."),
  GET_USER_SUCCESS(HttpStatus.OK, "유저 조회에 성공했습니다."),
  LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
  UPDATE_USER_SUCCESS(HttpStatus.OK, "유저 정보 수정에 성공했습니다."),
  DELETE_USER_SUCCESS(HttpStatus.OK, "회원 탈퇴에 성공했습니다."),

  POST_CREATE_SUCCESS(HttpStatus.CREATED, "게시글 생성에 성공했습니다."),
  POST_READ_SUCCESS(HttpStatus.OK, "게시글 조회에 성공했습니다."),
  POST_UPDATE_SUCCESS(HttpStatus.OK, "게시글 수정에 성공했습니다."),
  POST_DELETE_SUCCESS(HttpStatus.OK, "게시글 삭제에 성공했습니다."),
  POST_SEARCH_SUCCESS(HttpStatus.OK, "게시글 검색에 성공했습니다."),

  COMMENT_CREATE_SUCCESS(HttpStatus.CREATED, "댓글 생성에 성공했습니다."),
  COMMENT_READ_SUCCESS(HttpStatus.OK, "댓글 생성에 성공했습니다."),
  COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글 수정에 성공했습니다."),
  COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글 삭제에 성공했습니다."),

  IMAGE_UPLOAD_SUCCESS(HttpStatus.OK, "이미지 업로드에 성공했습니다."),
  ;

  private final HttpStatus httpStatus;
  private final String message;
}