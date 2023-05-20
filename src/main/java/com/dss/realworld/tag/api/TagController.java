package com.dss.realworld.tag.api;

import com.dss.realworld.tag.api.dto.TagResponseDto;
import com.dss.realworld.tag.app.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping(value = "/tags")
    public ResponseEntity<TagResponseDto> getAll() {
        TagResponseDto tags = tagService.getAll();

        return ResponseEntity.ok(tags);
    }
}