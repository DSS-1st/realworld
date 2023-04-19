package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getUserByUsername() {
        User newUser = User.builder()
                .username("Jacob")
                .password("jakejake")
                .email("jake@jake.jake")
                .build();
        userRepository.addUser(newUser);
        GetUserDto addedUser = userRepository.getUserByUsername("Jacob");
        Assertions.assertThat(addedUser.getUsername()).isEqualTo(newUser.getUsername());
    }
    @Test
    void getUserByEmail() {
        User newUser = User.builder()
                .username("Jacob")
                .password("jakejake")
                .email("jake@jake.jake")
                .build();
        userRepository.addUser(newUser);
        GetUserDto addedUser = userRepository.getUserByEmail("jake@jake.jake");
        Assertions.assertThat(addedUser.getEmail()).isEqualTo(newUser.getEmail());
    }
}
