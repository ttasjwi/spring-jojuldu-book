package com.ttasjwi.jojulduBook.web;

import com.google.gson.Gson;
import com.ttasjwi.jojulduBook.service.post.PostService;
import com.ttasjwi.jojulduBook.web.dto.PostSaveRequestDto;
import com.ttasjwi.jojulduBook.web.dto.PostSaveResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
