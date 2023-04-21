package com.dss.realworld.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    @Test
    void Should_Success_When_FieldsAreNotNull() {
        User user = User.builder()
                .username("Jacob")
                .password("jakejake")
                .email("jake@jake.jake")
                .build();

        assertThat(user.getUsername()).isEqualTo("Jacob");
        assertThat(user.getPassword()).isEqualTo("jakejake");
        assertThat(user.getEmail()).isEqualTo("jake@jake.jake");
    }

    @Test
    void Should_ThrowException_When_UsernameIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> User.builder()
                .password("jakejake")
                .email("jake@jake.jake")
                .build());
    }

    @Test
    void Should_ThrowException_When_PasswordIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> User.builder()
                .username("Jacob")
                .email("jake@jake.jake")
                .build());
    }

    @Test
    void Should_ThrowException_When_EmailIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> User.builder()
                .username("Jacob")
                .password("jakejake")
                .build());
    }
}
