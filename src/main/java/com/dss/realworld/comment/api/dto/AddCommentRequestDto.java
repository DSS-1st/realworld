package com.dss.realworld.comment.api.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonTypeName(value = "comment")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequestDto {

    private String body;
}
