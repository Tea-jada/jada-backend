package com.tea.web.community.post.domain.model;

public enum Section {
    OPINION("오피니언"),
    TEA_AND_NEWS("차와 뉴스"),
    TEA_AND_CULTURE("차와 문화"),
    TEA_AND_PEAPLE("차와 사람"),
    TEA_AND_WORLD("차와 세계"),
    TEA_AND_ART("차와 예술");

    private final String description;

    Section(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
