package com.dss.realworld.article.domain;

import java.util.Objects;

public final class Slug {

    private final String title;
    private final Long maxArticleId;

    private Slug(final String title, final Long maxArticleId) {
        this.title = title;
        this.maxArticleId = maxArticleId;
    }

    public static Slug of(final String title, final Long maxArticleId) {
        return new Slug(title, maxArticleId);
    }

    public String getString() {
        return this.title.trim().toLowerCase().replace(" ", "-") + '-' + (this.maxArticleId + 1);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Slug slug = (Slug) o;
        return title.equals(slug.title) && maxArticleId.equals(slug.maxArticleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, maxArticleId);
    }
}