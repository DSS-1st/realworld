package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.FollowRelation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FollowRelationRepositoryTest {

    @Autowired
    private FollowRelationRepository followRelationRepository;

    @BeforeEach
    void setUp() {
        clearTable();
    }

    @AfterEach
    void teatDown() {
        clearTable();
    }

    private void clearTable() {
        followRelationRepository.deleteAll();
        followRelationRepository.resetAutoIncrement();
    }

    @DisplayName(value = "followRelation 유효하면 저장 성공")
    @Test
    void t1() {
        Long followeeId = 1L;
        Long followerId = 2L;
        FollowRelation followRelation = new FollowRelation(followeeId, followerId);

        int save = followRelationRepository.save(followRelation);

        assertThat(save).isEqualTo(1);
    }
}