package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    void deleteAll();

    void resetAutoIncrement();

    void persist(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(Long id);
}
