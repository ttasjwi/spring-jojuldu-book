package com.ttasjwi.jojulduBook.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HelloController.class) // @WebMvcTest : web layer의 필요한 빈만 가져와서 테스트
class HelloControllerTest {

    @Autowired
    private MockMvc mvc; // 웹 API를 테스트할 때 사용

    @Test
    @DisplayName("'/hello'로 get 요청 → 'hello'가 리턴된다.")
    public void helloTest() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    @DisplayName("'/hello/dto'로 파라미터 전달 요청 -> dto가 응답된다.")
    public void helloDtoTest() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                    .param("name", name) // 파라미터 지정
                    .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk()) // status
                .andExpect(jsonPath("$.name", is(name))) // 응답 json
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
