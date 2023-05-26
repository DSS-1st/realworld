package com.dss.realworld.tag.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class TagTest {

    @DisplayName(value = "name이 유효하면 Tag 생성 성공")
    @Test
    void t1() {
        //given
        String name = "java";

        //when
        Tag newTag = new Tag(name);

        //then
        assertThat(newTag.getName()).isEqualTo(name);
    }

    @DisplayName(value = "name이 null이면 예외 발생")
    @Test
    void t2() {
        //given
        //when
        //then
        assertThatIllegalArgumentException().isThrownBy(() -> new Tag(null));
    }
}