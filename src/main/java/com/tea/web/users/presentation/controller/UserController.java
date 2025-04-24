package com.tea.web.users.presentation.controller;

import com.tea.web.common.ResponseMessageDto;
import com.tea.web.common.ResponseStatus;
import com.tea.web.users.application.dto.request.AdminSignupRequestDto;
import com.tea.web.users.application.dto.request.SignupRequestDto;
import com.tea.web.users.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * 일반 회원가입
     * @param signupRequestDto
     * @return "회원가입에 성공했습니다."
     */
    @PostMapping("/signup")
    public ResponseMessageDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return new ResponseMessageDto(ResponseStatus.SIGNUP_SUCCESS);
    }

    /**
     * 어드민 회원가입
     * @param requestDto
     * @return "회원가입에 성공했습니다."
     */
    @PostMapping("/signup/admin")
    public ResponseMessageDto adminSignup(@Valid @RequestBody AdminSignupRequestDto requestDto) {
        userService.adminSignup(requestDto);
        return new ResponseMessageDto(ResponseStatus.SIGNUP_SUCCESS);
    }
}
