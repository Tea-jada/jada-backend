package com.tea.web.community.post.presentation.controller;

import com.tea.web.common.ResponseDataDto;
import com.tea.web.common.ResponseMessageDto;
import com.tea.web.common.ResponseStatus;
import com.tea.web.community.post.application.dto.request.PostCreateRequestDto;
import com.tea.web.community.post.application.dto.request.PostUpdateRequestDto;
import com.tea.web.community.post.application.dto.response.PostListResponseDto;
import com.tea.web.community.post.application.dto.response.PostResponseDto;
import com.tea.web.community.post.application.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성
     * 
     * @param request     게시글 생성 요청 데이터
     * @param userDetails 현재 인증된 사용자 정보
     * @return 생성된 게시글 정보
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createPost(@RequestBody PostCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        postService.createPost(request, userDetails);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.POST_CREATE_SUCCESS));
    }

    /**
     * 게시글 상세 조회
     * 
     * @param postId 조회할 게시글 ID
     * @return 게시글 상세 정보
     */
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDataDto<PostResponseDto>> getPost(@PathVariable("postId") Long postId) {
        PostResponseDto responseDto = postService.getPost(postId);

        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.POST_READ_SUCCESS, responseDto));
    }

    // /**
    // * 게시글 목록 조회 (페이징)
    // *
    // * @param pageable 페이징 정보 (page: 페이지 번호(0부터 시작), size: 페이지 크기)
    // * @return 페이징된 게시글 목록 (id, title, content, thumbnailUrl, updatedAt)
    // * @example /api/v1/posts?page=0&size=10
    // */
    // @GetMapping
    // public ResponseEntity<ResponseDataDto<Page<PostListResponseDto>>>
    // getAllPosts(Pageable pageable) {
    // Page<PostListResponseDto> responseDtos = postService.getAllPosts(pageable);
    // return ResponseEntity.ok(new
    // ResponseDataDto<>(ResponseStatus.POST_READ_SUCCESS, responseDtos));
    // }

    /**
     * 게시글 수정
     * 
     * @param postId      수정할 게시글 ID
     * @param request     게시글 수정 요청 데이터
     * @param userDetails 현재 인증된 사용자 정보
     * @return 수정된 게시글 정보
     */
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDataDto<PostResponseDto>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        PostResponseDto responseDto = postService.updatePost(postId, request, userDetails);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.POST_UPDATE_SUCCESS, responseDto));
    }

    /**
     * 게시글 삭제
     * 
     * @param postId      삭제할 게시글 ID
     * @param userDetails 현재 인증된 사용자 정보
     * @return 204 No Content
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseMessageDto> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(postId, userDetails);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.POST_DELETE_SUCCESS));
    }

    /**
     * 게시글 검색 (제목 또는 작성자 이름으로 검색)
     * 
     * @param keyword  검색어 (게시글 제목 또는 작성자 이름)
     * @param pageable 페이징 정보 (page: 페이지 번호(0부터 시작), size: 페이지 크기)
     * @return 검색된 게시글 목록 (id, title, content, thumbnailUrl, updatedAt)
     * @example /api/v1/posts/search?keyword=검색어&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDataDto<Page<PostListResponseDto>>> searchPosts(
            @RequestParam String keyword,
            Pageable pageable) {
        Page<PostListResponseDto> responseDtos = postService.searchPosts(keyword, pageable);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.POST_SEARCH_SUCCESS, responseDtos));
    }

    /**
     * 카테고리별 게시글 목록 조회 (페이징)
     *
     * @param category 카테고리명 (예: notice, article, board)
     * @param pageable 페이징 정보
     * @return 페이징된 게시글 목록
     * @example /api/v1/posts/category/notice?page=0&size=10
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<ResponseDataDto<Page<PostListResponseDto>>> getPostsByCategory(
            @PathVariable("category") String category,
            Pageable pageable) {
        Page<PostListResponseDto> responseDtos = postService.getPostsByCategory(category, pageable);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.POST_READ_SUCCESS, responseDtos));
    }
}
