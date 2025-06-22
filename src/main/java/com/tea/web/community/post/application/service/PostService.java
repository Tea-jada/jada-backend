package com.tea.web.community.post.application.service;

import com.tea.web.community.post.application.dto.request.PostCreateRequestDto;
import com.tea.web.community.post.application.dto.request.PostUpdateRequestDto;
import com.tea.web.community.post.application.dto.response.PostListResponseDto;
import com.tea.web.community.post.application.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface PostService {
    void createPost(PostCreateRequestDto request, UserDetails userDetails);

    PostResponseDto getPost(Long postId);

    Page<PostListResponseDto> getAllPosts(Pageable pageable);

    Page<PostListResponseDto> searchPosts(String keyword, Pageable pageable);

    PostResponseDto updatePost(Long postId, PostUpdateRequestDto request, UserDetails userDetails);

    void deletePost(Long postId, UserDetails userDetails);
}
