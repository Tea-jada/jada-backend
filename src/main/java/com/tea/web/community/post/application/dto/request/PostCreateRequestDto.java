package com.tea.web.community.post.application.dto.request;

import com.tea.web.community.post.domain.model.Category;
import com.tea.web.community.post.domain.model.Section;
import com.tea.web.community.post.domain.model.SubSection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequestDto {
    private String type;
    private String title;
    private String content;
    private Category category;
    private String thumbnailUrl;
    private String img1l;
    private String img2l;
    private String img3l;
    private Section section;
    private SubSection subSection;
}