package com.dss.realworld.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SlugTest {

    @DisplayName(value = "정규 표현식 퍼센트 인코딩 문자 변경 확인")
    @Test
    void t1() {
        //given
        String title = " abc :/?#[ ]@!$&'\"`( )*+,.;=% def ";
        Long maxId = 1L;

        //when
        String value = Slug.of(title, maxId).getValue();
        System.out.println("value = " + value);

        //then
        assertThat(value).isEqualTo("abc--------------------------def-1");
    }

    @DisplayName(value = "정규 표현식 퍼센트 인코딩 문자 변경 확인(역순)")
    @Test
    void t2() {
        //given
        String title = " abc %=;.,+*) (`\"'&$!@] [#?/: def ";
        Long maxId = 10L;

        //when
        String value = Slug.of(title, maxId).getValue();
        System.out.println("value = " + value);

        //then
        assertThat(value).isEqualTo("abc--------------------------def-10");
    }

    @DisplayName(value = "정규 표현식 퍼센트 인코딩 문자 변경 확인(단어 사이)")
    @Test
    void t3() {
        //given
        String title = " abc a%b=c;d.e,f+g*h)i(j`k\"l'm&n$o!p@1]2[3#4?5/6:7 def ";
        Long maxId = 10L;

        //when
        String value = Slug.of(title, maxId).getValue();
        System.out.println("value = " + value);

        //then
        assertThat(value).isEqualTo("abc-a-b-c-d-e-f-g-h-i-j-k-l-m-n-o-p-1-2-3-4-5-6-7-def-10");
    }
}