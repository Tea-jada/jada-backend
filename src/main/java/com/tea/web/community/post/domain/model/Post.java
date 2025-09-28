package com.tea.web.community.post.domain.model;

import com.tea.web.common.BaseEntity;
import com.tea.web.community.category.domain.model.Category;
import com.tea.web.community.category.domain.model.SubCategory;
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
    @Column(columnDefinition = "text", nullable = false)
    private String content; // HTML 형식으로 저장

    // @Enumerated(EnumType.STRING)
    // @Column(length = 30, nullable = false)
    // private Category category;

    @Column(name = "thumbnail_url", length = 600, nullable = false)
    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY) // 여러 Post가 한 Category에 속함
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY) // 여러 Post가 한 SubCategory에 속함
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    // @Column(length = 20, nullable = false)
    // private Section section;

    // @Column(length = 10, nullable = false)
    // private SubSection subSection;

    @Builder
    public Post(User user, String type, String title, String content, Category category, SubCategory subCategory,
            String thumbnailUrl, Section section, SubSection subSection) {
        this.user = user;
        this.type = type;
        this.title = title;
        this.content = content;
        this.category = category;
        this.subCategory = subCategory;
        this.thumbnailUrl = thumbnailUrl;
        // this.section = section;
        // this.subSection = subSection;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete(String email) {
        softDeletedBy(email);
    }
}