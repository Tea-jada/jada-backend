package com.tea.web.users.presentation.controller;

import com.tea.web.common.ResponseDataDto;
import com.tea.web.common.ResponseMessageDto;
import com.tea.web.common.ResponseStatus;
import com.tea.web.users.application.dto.request.AdminSignupRequestDto;
import com.tea.web.users.application.dto.request.SignupRequestDto;
import com.tea.web.users.application.dto.response.UserInfoResponseDto;
import com.tea.web.users.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * 일반 회원가입
     * 
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
     * 
     * @param requestDto
     * @return "회원가입에 성공했습니다."
     */
    @PostMapping("/signup/admin")
    public ResponseMessageDto adminSignup(@Valid @RequestBody AdminSignupRequestDto requestDto) {
        userService.adminSignup(requestDto);
        return new ResponseMessageDto(ResponseStatus.SIGNUP_SUCCESS);
    }

    /**
     * 사용자 목록 조회 (어드민 전용)
     * 
     * @param pageable
     * @return 유저 정보 페이징(id, email, username, isActive, role)
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public ResponseDataDto<Page<UserInfoResponseDto>> getUserInfos(
            @PageableDefault Pageable pageable) {
        Page<UserInfoResponseDto> userInfos = userService.getUserInfos(pageable);
        return new ResponseDataDto<>(ResponseStatus.GET_USER_SUCCESS, userInfos);
    }

    /**
     * 사용자 정보 조회
     * 
     * @param userDetails
     * @return 사용자 정보 조회
     */
    @GetMapping("/info/{userId}")
    public ResponseDataDto<UserInfoResponseDto> getMyInfo(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserInfoResponseDto userInfo = userService.getMyInfo(userId, userDetails);
        return new ResponseDataDto<>(ResponseStatus.GET_USER_SUCCESS, userInfo);
    }
}
