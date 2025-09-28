package com.tea.web.community.category.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.tea.web.common.BaseEntity;
import com.tea.web.community.post.domain.model.Post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "c_sub_category")
@Getter
@NoArgsConstructor
public class SubCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String subCategoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public SubCategory(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public void update(String subCategoryName, String email) {
        this.subCategoryName = subCategoryName;
        updatedBy(email);
    }

    public void delete(String email) {
        softDeletedBy(email);
    }
}
