package com.dss.realworld.comment.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CommentListResponseDto {

    private final List<CommentDto> comments;
}