package com.tea.web.community.post.application.service;

import com.tea.web.common.CustomException;
import com.tea.web.common.ErrorType;
import com.tea.web.community.post.application.dto.request.PostCreateRequestDto;
import com.tea.web.community.post.application.dto.request.PostUpdateRequestDto;
import com.tea.web.community.post.application.dto.response.PostResponseDto;
import com.tea.web.community.post.application.dto.response.PostListResponseDto;
import com.tea.web.community.post.domain.model.Post;
import com.tea.web.community.post.domain.repository.PostRepository;
import com.tea.web.users.domain.model.User;
import com.tea.web.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 생성
     * 
     * @param request
     * @param userDetails
     * @return
     */
    @Override
    @Transactional
    public void createPost(PostCreateRequestDto request, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorType.USER_NOT_FOUND));

        Post post = Post.builder()
                .user(user)
                .type(request.getType())
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .thumbnailUrl(request.getThumbnailUrl())
                .img1l(request.getImg1l())
                .img2l(request.getImg2l())
                .img3l(request.getImg3l())
                .build();

        postRepository.save(post);
    }

    @Override
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorType.POST_NOT_FOUND));
        return convertToResponseDto(post);
    }

    @Override
    public Page<PostListResponseDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::convertToListResponseDto);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto request, UserDetails userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorType.POST_NOT_FOUND));

        // 게시글 작성자 확인
        if (!post.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new CustomException(ErrorType.FORBIDDEN);
        }

        post.update(request.getTitle(), request.getContent());
        return convertToResponseDto(post);
    }

    @Override
    @Transactional
    public void deletePost(Long postId, UserDetails userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorType.POST_NOT_FOUND));

        // 게시글 작성자 확인
        if (!post.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new CustomException(ErrorType.FORBIDDEN);
        }

        postRepository.deleteById(postId);
    }

    @Override
    public Page<PostListResponseDto> searchPosts(String keyword, Pageable pageable) {
        return postRepository.searchByTitleOrUsername(keyword, pageable)
                .map(this::convertToListResponseDto);
    }

    private PostResponseDto convertToResponseDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .type(post.getType())
                .category(post.getCategory())
                .thumbnailUrl(post.getThumbnailUrl())
                .img1l(post.getImg1l())
                .img2l(post.getImg2l())
                .img3l(post.getImg3l())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    private PostListResponseDto convertToListResponseDto(Post post) {
        return PostListResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnailUrl(post.getThumbnailUrl())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}