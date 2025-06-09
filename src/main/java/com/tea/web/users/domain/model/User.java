package com.tea.web.users.domain.model;

import com.tea.web.common.BaseEntity;
import com.tea.web.community.post.domain.model.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
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
    private String isActive; // 계정 활성화 "Y" or "N"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public static User from(String email, String username, String password, Role role) {
        User user = User.builder()
                .email(email)
                .username(username)
                .password(password)
                .isActive("Y") // 처음 회원가입 시 "Y"
                .role(role)
                .build();
        user.createdBy(email);
        return user;
    }
}
