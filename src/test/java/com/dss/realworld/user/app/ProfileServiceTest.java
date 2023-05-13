package com.dss.realworld.user.app;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.user.api.GetProfileDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.UserFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        clearTable();

        User newUser = UserFixtures.create();
        userRepository.persist(newUser);
    }

    @AfterEach
    void teatDown() {
        clearTable();
    }

    private void clearTable() {
        userRepository.deleteAll();
        userRepository.resetAutoIncrement();
    }
    @DisplayName("username이 유효하면 GetProfileDto 가져오기 성공")
    @Test
    void t1() {
        GetProfileDto profileDto = profileService.getProfileDto("Jacob000");

        assertThat(profileDto.getUsername()).isEqualTo("Jacob000");
    }
}