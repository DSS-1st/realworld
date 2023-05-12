package com.dss.realworld.article.domain;

import lombok.Getter;

@Getter
public final class Slug {

    private final String value;

    private Slug(final String title, final Long maxArticleId, final Boolean isCreation) {
        this.value = title.trim().toLowerCase().replace(" ", "-") + '-' + (maxArticleId + isCreation.compareTo(false));
    }

    public static Slug of(final String title, final Long maxArticleId, final Boolean isCreation) {
        return new Slug(title, maxArticleId, isCreation);
    }
}