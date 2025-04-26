package com.tea.web.users.application.service;

import com.tea.web.common.CustomException;
import com.tea.web.common.ErrorType;
import com.tea.web.users.application.dto.request.AdminSignupRequestDto;
import com.tea.web.users.application.dto.request.SignupRequestDto;
import com.tea.web.users.application.dto.response.UserInfoResponseDto;
import com.tea.web.users.domain.model.Role;
import com.tea.web.users.domain.model.User;
import com.tea.web.users.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("1234")
    private String adminCode;

    /**
     * 일반 회원가입
     * @param signupRequestDto
     */
    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        Role role = Role.USER;

        User user = User.from(signupRequestDto.getEmail(),
                signupRequestDto.getUsername(),
                signupRequestDto.getPassword(),
                role
        );

        userRepository.save(user);
    }

    /**
     * 어드민 회원가입
     * @param requestDto
     */
    @Transactional
    public void adminSignup(AdminSignupRequestDto requestDto) {
        if (requestDto.getAdminCode().equals(adminCode)) {
            throw new CustomException(ErrorType.INVALID_ADMIN_CODE);
        }

        Role role = Role.ADMIN;

        User admin = User.from(requestDto.getEmail(),
                requestDto.getUsername(),
                requestDto.getPassword(),
                role
        );

        userRepository.save(admin);
    }

    public Page<UserInfoResponseDto> getUserInfos(Pageable pageable) {
        Page<User> usersPage = userRepository.findAllByIsDeletedFalse(pageable);
        return usersPage.map(UserInfoResponseDto::new);
    }
}
