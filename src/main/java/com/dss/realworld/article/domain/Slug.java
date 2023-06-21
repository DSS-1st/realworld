package com.dss.realworld.article.domain;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
public final class Slug {

    private final String value;

    private Slug(final String title, final Long articleId) {
        this.value = title.trim().toLowerCase().replaceAll("[:/?#\\[\\]@!$&'\"`()*+,.;=%\\s]", "-") + '-' + articleId;
    }

    public static Slug of(final String title, final Long articleId) {
        Assert.hasText(title, "title can not be null and space only");
        Assert.notNull(articleId, "articleId can not be null");

        return new Slug(title, articleId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        final Slug slug = (Slug) o;
        return Objects.equals(getValue(), slug.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}