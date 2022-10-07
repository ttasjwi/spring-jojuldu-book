package com.ttasjwi.jojulduBook.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = {"title", "content"})
@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {

    private String title;
    private String content;

    @Builder
    public PostUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
