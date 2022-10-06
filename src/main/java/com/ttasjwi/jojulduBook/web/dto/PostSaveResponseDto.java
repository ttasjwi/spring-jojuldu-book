package com.ttasjwi.jojulduBook.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(of = "postId")
@NoArgsConstructor
public class PostSaveResponseDto {

    private Long postId;

    public PostSaveResponseDto(Long postId) {
        this.postId = postId;
    }
}
