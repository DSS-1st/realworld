package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {
    void persist(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(Long id);

    void update(@Param(value = "user") User user, @Param(value = "userId") Long userId);
}