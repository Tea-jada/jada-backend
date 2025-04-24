package com.tea.web.users.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    @Email(message = "유효하지 않은 이메일 양식입니다.")
    private String email;

    private String username;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,150}$",
            message = "비밀번호는 최소 8자 이상, 영어/숫자/특수문자를 모두 포함해야 합니다."
    )
    private String password;
}
