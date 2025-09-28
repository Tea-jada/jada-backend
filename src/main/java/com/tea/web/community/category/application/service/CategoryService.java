package com.tea.web.community.category.application.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tea.web.common.CustomException;
import com.tea.web.common.ErrorType;
import com.tea.web.community.category.application.dto.request.CategoryRequestDto;
import com.tea.web.community.category.application.dto.response.CategoryResponseDto;
import com.tea.web.community.category.domain.model.Category;
import com.tea.web.community.category.domain.repository.CategoryRepository;
import com.tea.web.users.domain.model.Role;
import com.tea.web.users.domain.model.User;
import com.tea.web.users.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createCategory(CategoryRequestDto request, UserDetails userDetails) {
        checkNotAdmin(userDetails);

        Category category = new Category(request.getCategory());

        categoryRepository.save(category);
    }

    public List<CategoryResponseDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponseDto::new)
                .toList();
    }

    public CategoryResponseDto getCategory(Long categoryId) {
        Category category = findCategory(categoryId);

        return new CategoryResponseDto(category);
    }

    @Transactional
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto request, UserDetails userDetails) {
        User user = checkNotAdmin(userDetails);

        Category category = findCategory(categoryId);

        category.update(request.getCategory(), user.getEmail());
        return new CategoryResponseDto(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId, UserDetails userDetails) {
        User user = checkNotAdmin(userDetails);

        Category category = findCategory(categoryId);

        category.delete(user.getEmail());
        categoryRepository.save(category);
    }

    private User checkNotAdmin(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

        if (!user.getRole().equals(Role.ADMIN))
            throw new CustomException(ErrorType.NOT_ADMIN);

        return user;
    }

    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorType.CATEGORY_NOT_FOUND));
    }
}
