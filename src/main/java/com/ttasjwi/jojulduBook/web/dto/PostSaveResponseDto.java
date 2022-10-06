package com.ttasjwi.jojulduBook.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString(of = "postId")
@RequiredArgsConstructor
public class PostSaveResponseDto {

    private final Long postId;

}
