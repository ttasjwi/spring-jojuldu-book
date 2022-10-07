package com.ttasjwi.jojulduBook.web;

import com.google.gson.Gson;
import com.ttasjwi.jojulduBook.domain.post.Post;
import com.ttasjwi.jojulduBook.service.post.PostService;
import com.ttasjwi.jojulduBook.web.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(PostApiController.class)
class PostApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    PostService postService;

    @Test
    @DisplayName("post 등록 요청 컨트롤러 테스트")
    public void saveTest() throws Exception {
        String title = "title";
        String content = "content";
        String author = "author";

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        // Mock Service에서 반환해줄 return을 정의해둠
        PostSaveResponseDto responseDto = new PostSaveResponseDto(1L);
        given(postService.save(any())).willReturn(responseDto);

        Gson gson = new Gson();
        String bodyContent = gson.toJson(requestDto); // 객체를 json 문자열로 변환

        mvc.perform(post("/api/v1/posts")
                        .content(bodyContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").exists())
                .andDo(print());

        verify(postService).save(any()); // 지정한 메서드가 실행됐는가?
    }

    @Test
    @DisplayName("id로 post 조회 - 컨트롤러 단위 테스트")
    public void findByIdTest() throws Exception {
        Long postId = 1L;
        String title = "title";
        String content = "content";
        String author = "author";

        Post mockPost = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        given(postService.findById(postId)).willReturn(new PostResponseDto(mockPost));

        mvc.perform(get("/api/v1/posts/" + postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.content", is(content)))
                .andExpect(jsonPath("$.author", is(author)));

        verify(postService).findById(postId);
    }


    @Test
    @DisplayName("post update - 컨트롤러 단위 테스트")
    public void updateTest() throws Exception {
        Long postId = 1L;
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();
        given(postService.update(anyLong(), any(PostUpdateRequestDto.class))).willReturn(new PostUpdateResponseDto(postId));

        Gson gson = new Gson();
        String bodyContent = gson.toJson(requestDto); // 객체를 json 문자열로 변환

        mvc.perform(put("/api/v1/posts/" + postId)
                        .content(bodyContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1L))
                .andDo(print());

        verify(postService).update(anyLong(), any(PostUpdateRequestDto.class));
    }

}
