package com.dss.realworld.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    void addUser(User user);
}
