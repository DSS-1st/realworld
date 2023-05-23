package com.dss.realworld.tag.app;

import com.dss.realworld.error.exception.CustomApiException;
import com.dss.realworld.tag.api.dto.TagResponseDto;
import com.dss.realworld.tag.domain.Tag;
import com.dss.realworld.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Transactional
    @Override
    public void persist(Set<Tag> tagSet) {
        try {
            tagSet.forEach(element -> tagRepository.persist(element));
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("이미 존재하는 이름입니다.");
        }
    }

    @Override
    public TagResponseDto getAll() {
        List<String> result = tagRepository.getAll();
        if (result.size() == 0) throw new CustomApiException("저장된 Tag가 없습니다.");

        return new TagResponseDto(result);
    }

    @Override
    public String findByName(String name) {
        String result = tagRepository.findByName(name);
        if (result == null) throw new CustomApiException("해당하는 Tag가 없습니다.");

        return result;
    }

    @Transactional
    @Override
    public int delete(String name) {
        return tagRepository.delete(name);
    }
}