package com.dss.realworld.article.domain;

public class Slug {

    public static String getSlug(String title, Long maxArticleId) {
        return title.trim().toLowerCase().replace(" ", "-") + "-" + (maxArticleId + 1);
    }
}