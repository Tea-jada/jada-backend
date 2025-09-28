package com.tea.web.community.category.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tea.web.common.ResponseMessageDto;
import com.tea.web.common.ResponseStatus;
import com.tea.web.community.category.application.dto.request.CategoryCreateRequestDto;
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
    public ResponseEntity<ResponseMessageDto> createPost(@RequestBody CategoryCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        categoryService.createCategory(request, userDetails);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.CATEGORY_CREATE_SUCCESS));
    }

    /**
     * 카테고리 조회 (전체 조회)
     */

    /**
     * 카테고리 단일 조회
     */

    /**
     * 카테고리 수정
     */

    /**
     * 카테고리 삭제
     */
}
