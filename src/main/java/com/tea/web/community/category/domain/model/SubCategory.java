package com.tea.web.community.category.domain.model;

import com.tea.web.common.BaseEntity;
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
    private String subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public SubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void update(String subCategory, String email) {
        this.subCategory = subCategory;
        updatedBy(email);
    }

    public void delete(String email) {
        softDeletedBy(email);
    }
}
