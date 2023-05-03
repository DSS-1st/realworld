package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    void deleteAll();

    void resetAutoIncrement();

    void add(User user);

    GetUserDto getByUsername(String username);

    GetUserDto getByEmail(String email);

    GetUserDto getById(Long id);
}
