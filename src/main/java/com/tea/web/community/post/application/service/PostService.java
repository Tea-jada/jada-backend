package com.tea.web.community.post.application.service;

import com.tea.web.community.post.application.dto.request.PostCreateRequestDto;
import com.tea.web.community.post.application.dto.request.PostUpdateRequestDto;
import com.tea.web.community.post.application.dto.response.PostResponseDto;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public interface PostService {
    PostResponseDto createPost(PostCreateRequestDto request, UserDetails userDetails);

    PostResponseDto getPost(Long postId);

    List<PostResponseDto> getAllPosts();

    PostResponseDto updatePost(Long postId, PostUpdateRequestDto request);

    void deletePost(Long postId);
}
