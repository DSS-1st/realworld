package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    void addUser(User user);
    GetUserDto getUserByUsername(String username);
    GetUserDto getUserByEmail(String email);
    GetUserDto getUserById(Long id);
}
