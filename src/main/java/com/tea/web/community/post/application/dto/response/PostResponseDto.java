package com.tea.web.community.post.application.dto.response;

import com.tea.web.community.post.domain.model.Category;
import com.tea.web.community.post.domain.model.Section;
import com.tea.web.community.post.domain.model.SubSection;

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
    private Category category;
    private String thumbnailUrl;
    private String img1l;
    private String img2l;
    private String img3l;
    private Section section;
    private SubSection subSection;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}