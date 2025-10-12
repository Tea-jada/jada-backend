package com.tea.web.community.post.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FeaturedPostResponseDto {
    private Long id;
    private Long postId;
    private String title;
    private String content;
    private String type;
    private String thumbnailUrl;
    private String category;
    private String subCategory;
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
