package com.dss.realworld.tag.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TagResponseDto {

    List<String> tags;
}
