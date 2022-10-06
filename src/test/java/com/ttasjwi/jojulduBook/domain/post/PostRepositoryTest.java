package com.ttasjwi.jojulduBook.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("게시글 저장 및 불러오기")
    public void post_Save_and_Find() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "ttasjwi";

        Post savePost = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        //when
        postRepository.save(savePost);
        log.info("savePost = {}", savePost);

        Post findPost = postRepository.findById(savePost.getId()).get();
        log.info("findPost = {}", findPost);

        //then
        assertThat(findPost.getTitle()).isEqualTo(title);
        assertThat(findPost.getContent()).isEqualTo(content);
        assertThat(findPost.getAuthor()).isEqualTo(author);
    }
}
