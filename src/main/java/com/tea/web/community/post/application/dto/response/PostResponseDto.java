package com.tea.web.community.post.application.dto.response;

import com.tea.web.community.post.domain.model.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String type;
    private Category category;
    private String thumbnailUrl;
    private String img1l;
    private String img2l;
    private String img3l;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}