package com.tea.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
  // 사용예시
  // user
  // DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이메일이 이미 사용 중입니다."),
  // INVALID_EMAIL_OR_PASSWORD(HttpStatus.LOCKED, "이메일 또는 비밀번호가 잘못되었습니다."),
  // NOT_FOUND_USER(HttpStatus.LOCKED, "존재하지 않는 회원입니다."),
  // NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "이메일이 존재하지 않습니다."),
  // NOT_AVAILABLE_PERMISSION(HttpStatus.LOCKED, "권한이 없습니다."),
  // ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
  // LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 실패"),
  // TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "Refresh Token이 존재하지 않습니다. 다시 로그인
  // 해주세요."),
  // NOT_ACCESS_TOKEN(HttpStatus.NOT_FOUND, "Access Token이 존재하지 않습니다."),
  // UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "본인 정보만 접근할 수 있습니다.")
  INVALID_ADMIN_CODE(HttpStatus.BAD_REQUEST, "어드민 코드가 틀렸습니다."),
  UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "본인 정보만 접근할 수 있습니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
  DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
  INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 양식입니다."),
  INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호 형식이 올바르지 않습니다."),
  DELETED_USER(HttpStatus.UNAUTHORIZED, "이미 탈퇴한 계정입니다. 복구를 원하면 메일로 문의해주세요."),

  ADMIN_ONLY_POST(HttpStatus.FORBIDDEN, "어드민만 작성할 수 있습니다."),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
  FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
  CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
  SECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "섹션를 찾을 수 없습니다."),
  SUB_SECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "서브섹션를 찾을 수 없습니다."),

  COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
  NOT_COMMENT_AUTHOR(HttpStatus.FORBIDDEN, "본인이 작성한 댓글만 접근할 수 있습니다."),

  IMAGE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다."),
  NOT_ADMIN(HttpStatus.FORBIDDEN, "어드민이 아닙니다."),;

  private final HttpStatus httpStatus;
  private final String message;
}