package com.tea.web.community.category.application.dto.response;

import com.tea.web.community.category.domain.model.SubCategory;

import lombok.Getter;

@Getter
public class SubCategoryResponseDto {
    private Long subCategoryId;
    private String subCategoryName;

    public SubCategoryResponseDto(SubCategory subCategory) {
        this.subCategoryId = subCategory.getId();
        this.subCategoryName = subCategory.getSubCategoryName();
    }
}
