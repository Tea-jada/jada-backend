package com.tea.web.community.post.application.dto.request;

import com.tea.web.community.post.domain.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequestDto {
    private String title;
    private String content;
    private String type;
    private Category category;
    private String thumbnailUrl;
    private String img1l;
    private String img2l;
    private String img3l;
}