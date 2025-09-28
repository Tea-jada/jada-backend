package com.tea.web.community.post.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String type;
    // private Category category;
    private String thumbnailUrl;
    // private Section section;
    // private SubSection subSection;
    private String category;
    private String subCategory;
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}