package com.tea.web.community.post.domain.model;

public enum SubSection {
    ONE("1번 항목"),
    TWO("2번 항목"),
    THREE("3번 항목"),
    FOUR("4번 항목"),
    FIVE("5번 항목"),
    SIX("6번 항목"),
    SEVEN("7번 항목");

    private final String description;

    SubSection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
