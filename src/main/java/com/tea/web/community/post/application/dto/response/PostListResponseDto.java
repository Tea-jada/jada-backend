package com.tea.web.community.post.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponseDto {
    private Long id;
    private String title;
    private String content;
    private String thumbnailUrl;
    private LocalDateTime updatedAt;
}