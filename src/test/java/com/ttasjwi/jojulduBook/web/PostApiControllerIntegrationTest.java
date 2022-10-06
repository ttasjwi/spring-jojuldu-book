package com.ttasjwi.jojulduBook.web;

import com.ttasjwi.jojulduBook.domain.post.Post;
import com.ttasjwi.jojulduBook.domain.post.PostRepository;
import com.ttasjwi.jojulduBook.web.dto.PostSaveRequestDto;
import com.ttasjwi.jojulduBook.web.dto.PostSaveResponseDto;
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
        String title = "title";
        String content = "content";
        String author = "author";

        // given
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:"+port+"/api/v1/posts";

        // when
        ResponseEntity<PostSaveResponseDto> responseEntity = restTemplate.postForEntity(url, requestDto, PostSaveResponseDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getPostId()).isGreaterThan(0L);

        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

}
