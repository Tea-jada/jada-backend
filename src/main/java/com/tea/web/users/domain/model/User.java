package com.tea.web.users.domain.model;

import com.tea.web.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, length = 15)
    private String username;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 150)
    private String password;

    @Column(nullable = false, length = 1)
    private String isActive;    // 계정 활성화 "Y" or "N"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public static User from(String email, String username, String password, Role role) {
        User user = User.builder()
                .email(email)
                .username(username)
                .password(password)
                .role(role)
                .build();
        user.createdBy(email);
        return user;
    }
}
