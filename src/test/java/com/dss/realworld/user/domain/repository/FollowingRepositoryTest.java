package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.Following;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
public class FollowingRepositoryTest {

    @Autowired
    private FollowingRepository followingRepository;

    @DisplayName(value = "followRelation이 유효하면 저장 성공")
    @Test
    void t1() {
        Long loginId = 1L;
        Long targetId = 2L;
        Following following = new Following(loginId, targetId);

        int save = followingRepository.persist(following);

        assertThat(save).isEqualTo(1);
    }

    @DisplayName(value = "fromUserId, toUserId 유효하면 팔로우 취소 성공")
    @Test
    void t2() {
        Long loginId = 2L;
        Long targetId = 1L;
        Following following = new Following(targetId, loginId);

        followingRepository.persist(following);

        int result = followingRepository.delete(targetId, loginId);

        assertThat(result).isEqualTo(1);
    }
}