package com.tea.web.community.category.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tea.web.community.category.domain.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Optional<SubCategory> findBySubCategoryName(String subCategory);

}