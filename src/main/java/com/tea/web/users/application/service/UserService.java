package com.tea.web.users.application.service;

import com.tea.web.common.CustomException;
import com.tea.web.common.ErrorType;
import com.tea.web.users.application.dto.request.AdminSignupRequestDto;
import com.tea.web.users.application.dto.request.SignupRequestDto;
import com.tea.web.users.application.dto.request.UpdateUserInfoRequestDto;
import com.tea.web.users.application.dto.response.UserInfoResponseDto;
import com.tea.web.users.domain.model.Role;
import com.tea.web.users.domain.model.User;
import com.tea.web.users.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("1234")
    private String adminCode;

    /**
     * 일반 회원가입
     * 
     * @param signupRequestDto
     */
    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        User user = userRepository.findByEmail(signupRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorType.DUPLICATE_EMAIL));

        if (user.getIsDeleted()) {
            throw new CustomException(ErrorType.DELETED_USER);
        }

        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new CustomException(ErrorType.DUPLICATE_EMAIL);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        User newUser = User.from(signupRequestDto.getEmail(),
                signupRequestDto.getUsername(),
                encodedPassword,
                Role.USER);

        userRepository.save(newUser);
    }

    /**
     * 어드민 회원가입
     * 
     * @param requestDto
     */
    @Transactional
    public void adminSignup(AdminSignupRequestDto requestDto) {
        if (!requestDto.getAdminCode().equals(adminCode)) {
            throw new CustomException(ErrorType.INVALID_ADMIN_CODE);
        }

        Role role = Role.ADMIN;

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User admin = User.from(requestDto.getEmail(),
                requestDto.getUsername(),
                encodedPassword,
                role);

        userRepository.save(admin);
    }

    /**
     * 유저 목록 조회 (어드민 전용)
     * 
     * @param pageable
     * @return 유저 목록
     */
    @Transactional
    public Page<UserInfoResponseDto> getUserInfos(Pageable pageable) {
        Page<User> usersPage = userRepository.findAllByIsDeletedFalse(pageable);
        return usersPage.map(UserInfoResponseDto::new);
    }

    /**
     * 사용자 정보 조회
     * 
     * @param userDetails
     * @return 로그인한 사용자 정보
     */
    @Transactional
    public UserInfoResponseDto getMyInfo(Long userId, UserDetails userDetails) {
        User user = getUser(userId);

        checkUserAuthority(user, userDetails);

        return new UserInfoResponseDto(user);
    }

    /**
     * 사용자 정보 수정
     * 
     * @param userId
     * @param requestDto
     * @param userDetails
     */
    @Transactional
    public void updateUserInfo(Long userId, UpdateUserInfoRequestDto requestDto, UserDetails userDetails) {
        User user = getUser(userId);

        checkUserAuthority(user, userDetails);

        user.update(requestDto, passwordEncoder);
        userRepository.save(user);
    }

    /**
     * 사용자 탈퇴
     * 
     * @param userId
     * @param userDetails
     */
    @Transactional
    public void deleteUser(Long userId, UserDetails userDetails) {
        User user = getUser(userId);

        checkUserAuthority(user, userDetails);

        user.softDeletedBy(userDetails.getUsername());
        userRepository.save(user);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));
    }

    private void checkUserAuthority(User user, UserDetails userDetails) {
        String email = user.getEmail();

        // 어드민이 아닌 경우 본인 정보만 조회 가능
        if (userDetails.getAuthorities().equals(Role.USER)) {
            if (!email.equals(userDetails.getUsername())) {
                throw new CustomException(ErrorType.UNAUTHORIZED_USER);
            }
        }
    }
}
