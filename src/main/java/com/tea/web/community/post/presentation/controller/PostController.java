package com.tea.web.community.post.presentation.controller;

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
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(postService.createPost(request, userDetails));
    }

    /**
     * 게시글 상세 조회
     * 
     * @param postId 조회할 게시글 ID
     * @return 게시글 상세 정보
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    /**
     * 게시글 목록 조회 (페이징)
     * 
     * @param pageable 페이징 정보 (page: 페이지 번호(0부터 시작), size: 페이지 크기)
     * @return 페이징된 게시글 목록 (id, title, content, thumbnailUrl, updatedAt)
     * @example /api/v1/posts?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<Page<PostListResponseDto>> getAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    /**
     * 게시글 수정
     * 
     * @param postId      수정할 게시글 ID
     * @param request     게시글 수정 요청 데이터
     * @param userDetails 현재 인증된 사용자 정보
     * @return 수정된 게시글 정보
     */
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(postService.updatePost(postId, request, userDetails));
    }

    /**
     * 게시글 삭제
     * 
     * @param postId      삭제할 게시글 ID
     * @param userDetails 현재 인증된 사용자 정보
     * @return 204 No Content
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(postId, userDetails);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Page<PostListResponseDto>> searchPosts(
            @RequestParam String keyword,
            Pageable pageable) {
        return ResponseEntity.ok(postService.searchPosts(keyword, pageable));
    }
}
