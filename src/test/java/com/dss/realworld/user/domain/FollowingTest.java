package com.dss.realworld.user.domain;

import com.dss.realworld.error.exception.SelfFollowingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class FollowingTest {

    @DisplayName(value = "필수 입력값이 NotNull이면 성공")
    @Test
    void t1() {
        //given
        Long targetId = 2L;
        Long loginId = 1L;

        //when
        Following following = new Following(targetId, loginId);

        //then
        assertThat(following.getTargetId()).isEqualTo(targetId);
        assertThat(following.getLoginId()).isEqualTo(loginId);
    }

    @DisplayName(value = "targetId가 Null이면 예외 발생")
    @Test
    void t2() {
        //given
        Long targetId = null;
        Long loginId = 1L;

        //when
        //then
        assertThatIllegalArgumentException().isThrownBy(() -> new Following(targetId, loginId));
    }

    @DisplayName(value = "loginId가 Null이면 예외 발생")
    @Test
    void t3() {
        //given
        Long targetId = 2L;
        Long loginId = null;

        //when
        //then
        assertThatIllegalArgumentException().isThrownBy(() -> new Following(targetId, loginId));
    }

    @DisplayName(value = "targetId와 loginId가 같으면 예외 발생")
    @Test
    void t4() {
        //given
        Long targetId = 1L;
        Long loginId = 1L;

        //when
        //then
        assertThatThrownBy(() -> new Following(targetId, loginId)).isInstanceOf(SelfFollowingException.class).hasMessageContaining("자신을 팔로우할 수 없습니다.");
    }
}