package com.tea.web.users.application.dto.response;

import com.tea.web.users.domain.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordResponseDto {
    private Long id;
    private String password;

    public PasswordResponseDto(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
    }
}
