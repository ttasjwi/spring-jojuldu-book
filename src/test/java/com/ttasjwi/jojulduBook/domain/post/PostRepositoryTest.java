package com.ttasjwi.jojulduBook.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

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

    @Test
    public void save_BaseTime_Entity() {
        //given
        LocalDateTime now = LocalDateTime.of(2022, 01, 01, 0, 0, 0);

        Post savePost = Post.builder()
                .title("title")
                .content("content")
                .author("author")
                .build();

        postRepository.save(savePost);
        em.flush();

        Post findPost = postRepository.findById(savePost.getId()).get();
        LocalDateTime createdDateTime1 = findPost.getCreatedDateTime();
        LocalDateTime modifiedDateTime1 = findPost.getModifiedDateTime();
        log.info("[First] {}", findPost);

        findPost.update("updateTitle", "updateContent");
        em.flush();

        Post updatePost = postRepository.findById(savePost.getId()).get();
        LocalDateTime createdDateTime2 = updatePost.getCreatedDateTime();
        LocalDateTime modifiedDateTime2 = updatePost.getModifiedDateTime();
        log.info("[After Update] {}", updatePost);

        assertThat(createdDateTime1).isAfter(now);
        assertThat(modifiedDateTime1).isAfter(now);
        assertThat(createdDateTime2).isEqualTo(createdDateTime1);
        assertThat(modifiedDateTime2).isAfter(modifiedDateTime1);
    }
}
