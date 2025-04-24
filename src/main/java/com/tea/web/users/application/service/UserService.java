package com.tea.web.users.application.service;

import com.tea.web.users.application.dto.request.SignupRequestDto;
import com.tea.web.users.domain.model.Role;
import com.tea.web.users.domain.model.User;
import com.tea.web.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signup(SignupRequestDto signupRequestDto) {
        Role role = Role.USER;

        User user = User.from(signupRequestDto.getEmail(),
                signupRequestDto.getUsername(),
                signupRequestDto.getPassword(),
                role
        );

        userRepository.save(user);
    }
}
