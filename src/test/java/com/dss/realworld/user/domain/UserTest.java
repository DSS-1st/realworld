package com.dss.realworld.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class UserTest {

    @DisplayName(value = "필수 입력값이 NotNull이면 성공")
    @Test
    void t1() {
        User user = User.builder()
                .username("Jacob")
                .password("jakejake")
                .email("jake@jake.jake")
                .build();

        assertThat(user.getUsername()).isEqualTo("Jacob");
        assertThat(user.getPassword()).isEqualTo("jakejake");
        assertThat(user.getEmail()).isEqualTo("jake@jake.jake");
    }

    @DisplayName(value = "username이 null이면 예외 발생")
    @Test
    void t2() {
        assertThatIllegalArgumentException().isThrownBy(() -> User.builder()
                .password("jakejake")
                .email("jake@jake.jake")
                .build());
    }

    @DisplayName(value = "password가 null이면 예외 발생")
    @Test
    void t3() {
        assertThatIllegalArgumentException().isThrownBy(() -> User.builder()
                .username("Jacob")
                .email("jake@jake.jake")
                .build());
    }

    @DisplayName(value = "email이 null이면 예외 발생")
    @Test
    void t4() {
        assertThatIllegalArgumentException().isThrownBy(() -> User.builder()
                .username("Jacob")
                .password("jakejake")
                .build());
    }
}
