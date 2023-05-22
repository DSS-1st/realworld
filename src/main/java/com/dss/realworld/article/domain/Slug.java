package com.dss.realworld.article.domain;

import lombok.Getter;

@Getter
public final class Slug {

    private final String value;

    private Slug(final String title, final Long articleId) {
        this.value = title.trim().toLowerCase().replace(" ", "-") + '-' + articleId;
    }

    public static Slug of(final String title, final Long articleId) {
        return new Slug(title, articleId);
    }
}