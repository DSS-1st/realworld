package com.dss.realworld.tag.domain.repository;

import com.dss.realworld.tag.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TagRepository {

    void persist(Tag tag);

    int delete(String name);

    List<String> getAll();

    Optional<String> findByName(String name);

    Optional<Long> findIdByName(String name);
}