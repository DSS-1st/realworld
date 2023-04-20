package com.dss.realworld.user.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThatThrownBy(() -> User.builder()
                .password("jakejake")
                .email("jake@jake.jake")
                .build()).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void Should_ThrowException_When_PasswordIsNull() {
        assertThatThrownBy(() -> User.builder()
                .username("Jacob")
                .email("jake@jake.jake")
                .build()).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void Should_ThrowException_When_EmailIsNull() {
        assertThatThrownBy(() -> User.builder()
                .username("Jacob")
                .password("jakejake")
                .build()).isInstanceOf(IllegalArgumentException.class);
    }
}
