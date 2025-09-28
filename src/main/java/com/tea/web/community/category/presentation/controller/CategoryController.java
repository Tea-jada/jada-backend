package com.tea.web.community.category.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tea.web.common.ResponseDataDto;
import com.tea.web.common.ResponseMessageDto;
import com.tea.web.common.ResponseStatus;
import com.tea.web.community.category.application.dto.request.CategoryRequestDto;
import com.tea.web.community.category.application.dto.response.CategoryResponseDto;
import com.tea.web.community.category.application.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 생성
     * 
     * @param request     게시글 생성 요청 데이터
     * @param userDetails 현재 인증된 사용자 정보
     * @return 생성된 게시글 정보
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createCategory(@RequestBody CategoryRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        categoryService.createCategory(request, userDetails);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.CATEGORY_CREATE_SUCCESS));
    }

    /**
     * 카테고리 조회 (전체 조회)
     */
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<CategoryResponseDto>>> getCategories() {
        List<CategoryResponseDto> responseDto = categoryService.getCategories();
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CATEGORY_READ_SUCCESS, responseDto));
    }

    /**
     * 카테고리 단일 조회
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseDataDto<CategoryResponseDto>> getCategory(
            @PathVariable("categoryId") Long categoryId) {
        CategoryResponseDto responseDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CATEGORY_READ_SUCCESS, responseDto));
    }

    /**
     * 카테고리 수정
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseDataDto<CategoryResponseDto>> updateCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        CategoryResponseDto responseDto = categoryService.updateCategory(categoryId, request, userDetails);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CATEGORY_UPDATE_SUCCESS, responseDto));
    }

    /**
     * 카테고리 삭제
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseMessageDto> deleteCategory(
            @PathVariable("categoryId") Long categoryId,
            @AuthenticationPrincipal UserDetails userDetails) {
        categoryService.deleteCategory(categoryId, userDetails);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.CATEGORY_DELETED_SUCCESS));
    }

}
