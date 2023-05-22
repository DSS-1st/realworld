package com.dss.realworld.tag.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias(value = "Tag")
@Getter
@EqualsAndHashCode
@ToString
public class Tag {

    private Long id;

    private String name;

    public Tag(final String name) {
        this.name = name;
    }
}