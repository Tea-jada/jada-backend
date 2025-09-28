package com.tea.web.community.category.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tea.web.community.category.domain.model.Category;
import com.tea.web.community.category.domain.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Optional<SubCategory> findBySubCategoryName(String subCategory);

    List<SubCategory> findAllByCategory(Category category);

}