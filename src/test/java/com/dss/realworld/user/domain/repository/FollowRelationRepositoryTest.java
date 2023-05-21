package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.FollowRelation;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.util.UserFixtures;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(value = "classpath:db/FollowRelationTeardown.sql")
public class FollowRelationRepositoryTest {

    @Autowired
    private FollowRelationRepository followRelationRepository;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        clearTable();
    }

    @AfterEach
    void teatDown() {
        clearTable();
    }

    private void clearTable() {
        userRepository.deleteAll();
        userRepository.resetAutoIncrement();
    }

    @DisplayName(value = "followRelation 유효하면 저장 성공")
    @Test
    void t1() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        FollowRelation followRelation = new FollowRelation(fromUserId, toUserId);

        int save = followRelationRepository.save(followRelation);

        assertThat(save).isEqualTo(1);
    }

    @DisplayName(value = "fromUserId,toUserId 유효하면 팔로우 취소 성공")
    @Test
    void t2() {

        User fromUser = UserFixtures.create();
        User toUser = User.builder()
                .username("username")
                .password("1234")
                .email("@google.com")
                .build();

        userRepository.persist(fromUser);
        userRepository.persist(toUser);

        Long fromUserId = fromUser.getId();
        Long toUserId = toUser.getId();

        FollowRelation followRelation = new FollowRelation(fromUserId, toUserId);
        followRelationRepository.save(followRelation);

        int cancelFollow = followRelationRepository.delete(fromUserId, toUserId);

        assertThat(cancelFollow).isEqualTo(1);
    }
}