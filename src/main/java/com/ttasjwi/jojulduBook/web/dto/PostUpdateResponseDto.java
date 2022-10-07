package com.ttasjwi.jojulduBook.web.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = "postId")
@Getter
@NoArgsConstructor
public class PostUpdateResponseDto {

    private Long postId;

    public PostUpdateResponseDto(Long postId) {
        this.postId = postId;
    }
}
