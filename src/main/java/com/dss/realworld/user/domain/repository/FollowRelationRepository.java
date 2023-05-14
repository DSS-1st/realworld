package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.FollowRelation;
import com.dss.realworld.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowRelationRepository {
    int save(FollowRelation followRelation);

    void deleteAll();

    void resetAutoIncrement();
}
