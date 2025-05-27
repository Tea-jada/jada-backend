package com.tea.web.community.post.domain.model;

import com.tea.web.users.domain.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "c_post")
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 50, nullable = false)
    private String type;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private Category category;

    @Column(name = "thumbnail_url", length = 600, nullable = false)
    private String thumbnailUrl;

    @Column(name = "img1l", length = 600)
    private String img1l;

    @Column(name = "img2l", length = 600)
    private String img2l;

    @Column(name = "img3l", length = 600)
    private String img3l;
}