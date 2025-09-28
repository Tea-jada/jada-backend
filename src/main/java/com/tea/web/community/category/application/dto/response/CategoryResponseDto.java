package com.tea.web.community.category.application.dto.response;

import com.tea.web.community.category.domain.model.Category;

public class CategoryResponseDto {
    private String category;

    public CategoryResponseDto(Category category) {
        this.category = category.getCategory();
    }
}
