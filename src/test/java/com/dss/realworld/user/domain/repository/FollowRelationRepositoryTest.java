package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.FollowRelation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
public class FollowRelationRepositoryTest {

    @Autowired
    private FollowRelationRepository followRelationRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName(value = "followRelation이 유효하면 저장 성공")
    @Test
    void t1() {
        Long loginId = 1L;
        Long targetId = 2L;
        FollowRelation followRelation = new FollowRelation(loginId, targetId);

        int save = followRelationRepository.persist(followRelation);

        assertThat(save).isEqualTo(1);
    }

    @DisplayName(value = "fromUserId, toUserId 유효하면 팔로우 취소 성공")
    @Test
    void t2() {
        Long loginId = 1L;
        Long targetId = 2L;
        FollowRelation followRelation = new FollowRelation(targetId, loginId);

        int save = followRelationRepository.persist(followRelation);

        int result = followRelationRepository.delete(targetId, loginId);

        assertThat(result).isEqualTo(1);
    }
}