package com.dss.realworld.tag.app;

import com.dss.realworld.tag.api.dto.TagResponseDto;
import com.dss.realworld.tag.domain.Tag;

import java.util.Set;

public interface TagService {
    void persist(Set<Tag> tagSet);

    TagResponseDto getAll();

    String findByName(String name);

    int delete(String name);
}
