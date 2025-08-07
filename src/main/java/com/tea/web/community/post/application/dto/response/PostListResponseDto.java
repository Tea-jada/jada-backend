package com.tea.web.community.post.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import com.tea.web.community.post.domain.model.Section;
import com.tea.web.community.post.domain.model.SubSection;

@Getter
@Builder
public class PostListResponseDto {
    private Long id;
    private String title;
    private String content;
    private String thumbnailUrl;
    private Section section;
    private SubSection subSection;
    private String username;
    private LocalDateTime updatedAt;
}