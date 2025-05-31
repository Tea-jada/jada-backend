package com.tea.web.community.post.application.service;

import com.tea.web.community.post.application.dto.request.PostCreateRequestDto;
import com.tea.web.community.post.application.dto.request.PostUpdateRequestDto;
import com.tea.web.community.post.application.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface PostService {
    PostResponseDto createPost(PostCreateRequestDto request, UserDetails userDetails);

    PostResponseDto getPost(Long postId);

    Page<PostResponseDto> getAllPosts(Pageable pageable);

    Page<PostResponseDto> searchPosts(String keyword, Pageable pageable);

    PostResponseDto updatePost(Long postId, PostUpdateRequestDto request);

    void deletePost(Long postId);
}
