package com.tea.web.community.category.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tea.web.community.category.domain.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
