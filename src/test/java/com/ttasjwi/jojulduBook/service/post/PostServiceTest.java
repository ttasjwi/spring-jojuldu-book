package com.ttasjwi.jojulduBook.service.post;

import com.ttasjwi.jojulduBook.domain.post.Post;
import com.ttasjwi.jojulduBook.domain.post.PostRepository;
import com.ttasjwi.jojulduBook.exception.PostNotFoundException;
import com.ttasjwi.jojulduBook.web.dto.PostResponseDto;
import com.ttasjwi.jojulduBook.web.dto.PostSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    @DisplayName("findById 메서드에서, 존재하는 id로 조회 → Dto 반환")
    public void findByIdSuccessTest() {
        Long postId = 1L;

        String title = "title";
        String content = "content";
        String author = "author";

        Post findPost = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(findPost));

        PostResponseDto responseDto = postService.findById(postId);

        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getContent()).isEqualTo(content);
        assertThat(responseDto.getAuthor()).isEqualTo(author);

        verify(postRepository).findById(postId);
    }

    @Test
    @DisplayName("findById 메서드에서, 존재하지 않는 id로 조회 → NoSuchPostException 발생")
    public void findByIdFailureTest() {
        Long postId = 1L;

        when(postRepository.findById(postId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.findById(1L)).isInstanceOf(PostNotFoundException.class);
        verify(postRepository).findById(1L);
    }
}
