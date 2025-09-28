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
import com.tea.web.community.category.application.dto.request.SubCategoryRequestDto;
import com.tea.web.community.category.application.dto.response.SubCategoryResponseDto;
import com.tea.web.community.category.application.service.SubCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    /**
     * 서브 카테고리 생성
     * 
     * @param request     게시글 생성 요청 데이터
     * @param userDetails 현재 인증된 사용자 정보
     * @return 생성된 게시글 정보
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createSubCategory(@RequestBody SubCategoryRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        subCategoryService.createSubCategory(request, userDetails);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.CATEGORY_CREATE_SUCCESS));
    }

    /**
     * 서브 카테고리 조회 (전체 조회)
     */
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<SubCategoryResponseDto>>> getSubCategories() {
        List<SubCategoryResponseDto> responseDto = subCategoryService.getSubCategories();
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CATEGORY_READ_SUCCESS, responseDto));
    }

    /**
     * 서브 카테고리 단일 조회
     */
    @GetMapping("/{subcategoryId}")
    public ResponseEntity<ResponseDataDto<SubCategoryResponseDto>> getSubCategory(
            @PathVariable("subcategoryId") Long subcategoryId) {
        SubCategoryResponseDto responseDto = subCategoryService.getSubCategory(subcategoryId);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CATEGORY_READ_SUCCESS, responseDto));
    }

    /**
     * 서브 카테고리 수정
     */
    @PutMapping("/{subcategoryId}")
    public ResponseEntity<ResponseDataDto<SubCategoryResponseDto>> updateSubCategory(
            @PathVariable("subcategoryId") Long subcategoryId,
            @RequestBody SubCategoryRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        SubCategoryResponseDto responseDto = subCategoryService.updateSubCategory(subcategoryId, request, userDetails);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CATEGORY_UPDATE_SUCCESS, responseDto));
    }

    /**
     * 서브 카테고리 삭제
     */
    @DeleteMapping("/{subcategoryId}")
    public ResponseEntity<ResponseMessageDto> deleteSubCategory(
            @PathVariable("subcategoryId") Long subcategoryId,
            @AuthenticationPrincipal UserDetails userDetails) {
        subCategoryService.deleteSubCategory(subcategoryId, userDetails);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.CATEGORY_DELETED_SUCCESS));
    }
}
