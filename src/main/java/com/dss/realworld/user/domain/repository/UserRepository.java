package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {

    void deleteAll();

    void resetAutoIncrement();

    void persist(User user);

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    User findById(Long id);
}