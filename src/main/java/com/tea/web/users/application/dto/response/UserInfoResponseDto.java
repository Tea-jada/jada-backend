package com.tea.web.users.application.dto.response;

import com.tea.web.users.domain.model.Role;
import com.tea.web.users.domain.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {
    private Long id;
    private String email;
    private String username;
    private String isActive;
    private Role role;

    public UserInfoResponseDto (User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.isActive = user.getIsActive();
        this.role = user.getRole();
    }
}
