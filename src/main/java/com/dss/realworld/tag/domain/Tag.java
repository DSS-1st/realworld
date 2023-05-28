package com.dss.realworld.tag.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.springframework.util.Assert;

@Alias(value = "Tag")
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class Tag {

    private Long id;

    private String name;

    public Tag(final String name) {
        Assert.notNull(name, "name can not be null");

        this.name = name;
    }
}