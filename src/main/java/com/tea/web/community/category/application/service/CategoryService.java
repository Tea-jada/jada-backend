package com.tea.web.community.category.application.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tea.web.common.CustomException;
import com.tea.web.common.ErrorType;
import com.tea.web.community.category.application.dto.request.CategoryCreateRequestDto;
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
    public void createCategory(CategoryCreateRequestDto request, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

        if (!user.getRole().equals(Role.ADMIN))
            throw new CustomException(ErrorType.NOT_ADMIN);

        Category category = new Category(request.getCategory());

        categoryRepository.save(category);
    }

}
