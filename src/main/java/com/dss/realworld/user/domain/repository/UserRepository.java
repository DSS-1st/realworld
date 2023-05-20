package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {

    void deleteAll();

    void resetAutoIncrement();

    void persist(User user);

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    User findById(Long id);

    void update(User user,Long userId);
}