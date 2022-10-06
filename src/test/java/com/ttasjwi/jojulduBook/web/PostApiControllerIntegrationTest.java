package com.ttasjwi.jojulduBook.web;

import com.ttasjwi.jojulduBook.domain.post.Post;
import com.ttasjwi.jojulduBook.domain.post.PostRepository;
import com.ttasjwi.jojulduBook.web.dto.PostResponseDto;
import com.ttasjwi.jojulduBook.web.dto.PostSaveRequestDto;
import com.ttasjwi.jojulduBook.web.dto.PostSaveResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PostApiControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("저장 통합 테스트")
    public void savePostTest() {
        // given
        String title = "title";
        String content = "content";
        String author = "author";

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<PostSaveResponseDto> responseEntity = restTemplate.postForEntity(url, requestDto, PostSaveResponseDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getPostId()).isGreaterThan(0L);

        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("조회 통합 테스트")
    public void findTest() {
        // given
        String title = "title";
        String content = "content";
        String author = "author";

        Long postId = postSave(title, content, author);

        // when
        String findUrl = "http://localhost:" + port + "/api/v1/posts/" + postId;
        ResponseEntity<PostResponseDto> responseEntity = restTemplate.getForEntity(findUrl, PostResponseDto.class); // 조회
        PostResponseDto responseDto = responseEntity.getBody();

        log.info("[find Post] post = {}", responseDto);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseDto.getId()).isGreaterThan(0L);
        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getContent()).isEqualTo(content);
        assertThat(responseDto.getAuthor()).isEqualTo(author);
    }

    private Long postSave(String title, String content, String author) {
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        log.info("[post save request] {}", requestDto);
        String url = "http://localhost:" + port + "/api/v1/posts";
        ResponseEntity<PostSaveResponseDto> responseEntity = restTemplate.postForEntity(url, requestDto, PostSaveResponseDto.class);
        Long id = responseEntity.getBody().getPostId();

        log.info("[post save] id = {}", id);
        return id;
    }

}
