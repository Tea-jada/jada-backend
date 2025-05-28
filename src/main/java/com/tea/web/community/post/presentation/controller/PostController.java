package com.tea.web.community.post.presentation.controller;

import com.tea.web.community.post.application.dto.request.PostCreateRequestDto;
import com.tea.web.community.post.application.dto.request.PostUpdateRequestDto;
import com.tea.web.community.post.application.dto.response.PostResponseDto;
import com.tea.web.community.post.application.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성
     * 
     * @param request
     * @param userDetails
     * @return
     */
    // TODO: 게시글 생성 시 썸네일, 이미지 업로드 기능 추가
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(postService.createPost(request, userDetails));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto request) {
        return ResponseEntity.ok(postService.updatePost(postId, request));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
