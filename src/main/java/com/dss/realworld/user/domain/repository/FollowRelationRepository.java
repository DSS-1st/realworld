package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.FollowRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowRelationRepository {
    int save(FollowRelation followRelation);

    void deleteAll();

    void resetAutoIncrement();
}
