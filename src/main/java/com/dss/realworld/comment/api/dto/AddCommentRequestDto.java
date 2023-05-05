package com.dss.realworld.comment.api.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonRootName("comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequestDto {

    private String body;
}
