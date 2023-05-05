package com.dss.realworld.comment.api.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonRootName(value = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequestDto {

    private String body;
}
