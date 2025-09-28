package com.tea.web.community.category.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.tea.web.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

// public enum Category {
//     ARTICLE("기사"),
//     NOTICE("공지사항"),
//     BOARD("게시글");

//     private final String description;

//     Category(String description) {
//         this.description = description;
//     }

//     public String getDescription() {
//         return description;
//     }
// }

@Entity
@Table(name = "c_category")
@Getter
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String category;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCategory> subCategories = new ArrayList<>();

    public Category(String category) {
        this.category = category;
    }
}