package com.tea.web.community.category.application.dto.response;

import com.tea.web.community.category.domain.model.Category;

import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private Long CategoryId;
    private String categoryName;

    public CategoryResponseDto(Category category) {
        this.CategoryId = category.getId();
        this.categoryName = category.getCategoryName();
    }
}
