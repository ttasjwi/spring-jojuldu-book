package com.ttasjwi.jojulduBook.service.post;

import com.ttasjwi.jojulduBook.domain.post.Post;
import com.ttasjwi.jojulduBook.domain.post.PostRepository;
import com.ttasjwi.jojulduBook.web.dto.PostSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("PostService의")
class PostServiceTest {

    private PostRepository postRepository = mock(PostRepository.class); // @MockBean을 통해서 Mock을 빈으로 등록하는 방법도 있음

    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository);
    }

    @Test
    @DisplayName("saveTest 메서드에서, 내부적으로 리포지토리를 호출하는 지 테스트")
    public void saveTest() {
        when(postRepository.save(any(Post.class)))
                .then(returnsFirstArg());

        PostSaveRequestDto requestDto = PostSaveRequestDto
                .builder()
                .title("title")
                .content("content")
                .author("author")
                .build();

        postService.save(requestDto);
        verify(postRepository).save(any(Post.class)); // save 메서드가 호출됐는 지 검증
    }
}
