package com.tea.web.community.category.application.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tea.web.common.CustomException;
import com.tea.web.common.ErrorType;
import com.tea.web.community.category.application.dto.request.SubCategoryRequestDto;
import com.tea.web.community.category.application.dto.response.SubCategoryResponseDto;
import com.tea.web.community.category.domain.model.SubCategory;
import com.tea.web.community.category.domain.repository.SubCategoryRepository;
import com.tea.web.users.domain.model.Role;
import com.tea.web.users.domain.model.User;
import com.tea.web.users.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createSubCategory(SubCategoryRequestDto request, UserDetails userDetails) {
        checkNotAdmin(userDetails);

        SubCategory subCategory = new SubCategory(request.getSubCategory());

        subCategoryRepository.save(subCategory);
    }

    public List<SubCategoryResponseDto> getSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        return subCategories.stream()
                .map(SubCategoryResponseDto::new)
                .toList();
    }

    public SubCategoryResponseDto getSubCategory(Long subCategoryId) {
        SubCategory subCategory = findSubCategory(subCategoryId);

        return new SubCategoryResponseDto(subCategory);
    }

    @Transactional
    public SubCategoryResponseDto updateSubCategory(Long subCategoryId, SubCategoryRequestDto request,
            UserDetails userDetails) {
        User user = checkNotAdmin(userDetails);

        SubCategory subCategory = findSubCategory(subCategoryId);

        subCategory.update(request.getSubCategory(), user.getEmail());
        return new SubCategoryResponseDto(subCategory);
    }

    @Transactional
    public void deleteSubCategory(Long subCategoryId, UserDetails userDetails) {
        User user = checkNotAdmin(userDetails);

        SubCategory subCategory = findSubCategory(subCategoryId);

        subCategory.delete(user.getEmail());
        subCategoryRepository.save(subCategory);
    }

    private User checkNotAdmin(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

        if (!user.getRole().equals(Role.ADMIN))
            throw new CustomException(ErrorType.NOT_ADMIN);

        return user;
    }

    private SubCategory findSubCategory(Long subCategoryId) {
        return subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new CustomException(ErrorType.CATEGORY_NOT_FOUND));
    }
}
