package com.tea.web.community.post.domain.model;

public enum Category {
    ARTICLE("기사"),
    NOTICE("공지사항"),
    BOARD("게시글");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
