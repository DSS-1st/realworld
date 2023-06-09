package com.dss.realworld.tag.app;

import com.dss.realworld.common.error.exception.TagNotFoundException;
import com.dss.realworld.tag.api.dto.TagResponseDto;
import com.dss.realworld.tag.domain.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles(value = "test")
@Sql(value = "classpath:db/teardown.sql")
@SpringBootTest
public class TagServiceTest {

    @Autowired
    TagService tagService;

    @DisplayName(value = "중복이 제거된 tag 이름 저장 성공")
    @Test
    void t1() {
        //given
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("qwer"));
        tagSet.add(new Tag("qwer"));
        tagSet.add(new Tag("asdf"));

        //when
        tagService.persist(tagSet);

        //then
        TagResponseDto foundTags = tagService.getAll();
        assertThat(foundTags.getTags().size()).isEqualTo(2);
    }

    @DisplayName(value = "중복된 tag name 저장 시도 시에도 저장 성공")
    @Test
    void t2() {
        //given
        String duplicateTag = "qwer";

        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("asdf"));
        tagSet.add(new Tag(duplicateTag));
        tagService.persist(tagSet);

        Set<Tag> tagSet2 = new HashSet<>();
        tagSet2.add(new Tag(duplicateTag));

        //when
        //then
        assertThatThrownBy(() -> tagService.persist(tagSet2)).isInstanceOf(DuplicateKeyException.class).hasMessageContaining("이미 존재하는 이름입니다.");
    }

    @DisplayName(value = "tag name이 존재하면 삭제 성공")
    @Test
    void t3() {
        //given
        Set<Tag> tagSet = new HashSet<>();
        String name = "asdf";
        tagSet.add(new Tag("qwer"));
        tagSet.add(new Tag("qwer"));
        tagSet.add(new Tag(name));
        tagService.persist(tagSet);

        //when
        int result = tagService.delete(name);

        //then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName(value = "tag 전체 조회 시 List 반환 성공")
    @Test
    void t4() {
        //given
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("qwer"));
        tagSet.add(new Tag("asdf"));
        tagSet.add(new Tag("zxcv"));
        tagService.persist(tagSet);

        //when
        TagResponseDto foundTags = tagService.getAll();

        //then
        assertThat(foundTags.getTags().size()).isEqualTo(3);
    }

    @DisplayName(value = "tag 1건 조회 시 List로 반환 성공")
    @Test
    void t5() {
        //given
        String name = "qwer";
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(name));
        tagSet.add(new Tag("asdf"));
        tagSet.add(new Tag("zxcv"));
        tagService.persist(tagSet);

        //when
        String result = tagService.findByName(name);

        //then
        assertThat(result).isEqualTo(name);
        assertThat(result.contains(name)).isTrue();
    }

    @DisplayName(value = "존재하지 않는 tag name 조회 시 예외 발생")
    @Test
    void t6() {
        //given
        String name = "qwer";
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(name));
        tagService.persist(tagSet);

        //when
        //then
        assertThatThrownBy(() -> tagService.findByName("zxcv")).isInstanceOf(TagNotFoundException.class).hasMessageContaining("해당되는 Tag가 없습니다.");
    }
}