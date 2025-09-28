package com.tea.web.community.category.application.dto.response;

import com.tea.web.community.category.domain.model.Category;

import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private String categoryName;

    public CategoryResponseDto(Category category) {
        this.categoryName = category.getCategoryName();
    }
}
