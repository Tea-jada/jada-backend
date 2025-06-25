package com.tea.web.community.post.domain.model;

import com.tea.web.common.BaseEntity;
import com.tea.web.users.domain.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "c_post")
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

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

    @Lob // Text (65,535자(약 64KB, 일반 게시글 충분))로 DB 설계
    @Column(nullable = false)
    private String content; // HTML or Markdown

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

    @Builder
    public Post(User user, String type, String title, String content, Category category,
            String thumbnailUrl, String img1l, String img2l, String img3l) {
        this.user = user;
        this.type = type;
        this.title = title;
        this.content = content;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
        this.img1l = img1l;
        this.img2l = img2l;
        this.img3l = img3l;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}