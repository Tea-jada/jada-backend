package com.tea.web.community.category.application.dto.response;

import com.tea.web.community.category.domain.model.SubCategory;

import lombok.Getter;

@Getter
public class SubCategoryResponseDto {
    private String subCategory;

    public SubCategoryResponseDto(SubCategory subCategory) {
        this.subCategory = subCategory.getSubCategory();
    }
}
