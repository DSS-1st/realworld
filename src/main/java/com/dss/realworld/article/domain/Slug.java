package com.dss.realworld.article.domain;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public final class Slug {

    private final String value;

    private Slug(final String title, final Long articleId) {
        this.value = title.trim().toLowerCase().replaceAll("[\\&|[\\uFE30-\\uFFA0]|\\’|\\”|\\s\\?\\,\\.]+", "-") + '-' + articleId;
    }

    public static Slug of(final String title, final Long articleId) {
        Assert.hasText(title, "title can not be null and space only");
        Assert.notNull(articleId, "articleId can not be null");

        return new Slug(title, articleId);
    }
}