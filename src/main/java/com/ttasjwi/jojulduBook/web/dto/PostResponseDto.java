package com.ttasjwi.jojulduBook.web.dto;

import com.ttasjwi.jojulduBook.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = {"id", "title", "content", "author"})
@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
    }
}
