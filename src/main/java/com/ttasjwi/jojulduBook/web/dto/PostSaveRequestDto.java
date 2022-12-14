package com.ttasjwi.jojulduBook.web.dto;

import com.ttasjwi.jojulduBook.domain.post.Post;
import lombok.*;

@Getter
@ToString(of = {"title", "content", "author"})
@NoArgsConstructor
public class PostSaveRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder(access = AccessLevel.PUBLIC)
    public PostSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
