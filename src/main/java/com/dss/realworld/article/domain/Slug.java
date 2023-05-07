package com.dss.realworld.article.domain;

import lombok.Getter;

@Getter
public final class Slug {

    private final String value;

    private Slug(final String title, final Long maxArticleId) {
        this.value = title.trim().toLowerCase().replace(" ", "-") + '-' + (maxArticleId + 1);
    }

    public static Slug of(final String title, final Long maxArticleId) {
        return new Slug(title, maxArticleId);
    }
}