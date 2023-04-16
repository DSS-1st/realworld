package com.dss.realworld.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public void addUser(Users users) {
        userMapper.addUser(users);
    }

}
