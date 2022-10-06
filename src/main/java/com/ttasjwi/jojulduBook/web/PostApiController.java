package com.ttasjwi.jojulduBook.web;

import com.ttasjwi.jojulduBook.service.post.PostService;
import com.ttasjwi.jojulduBook.web.dto.PostSaveRequestDto;
import com.ttasjwi.jojulduBook.web.dto.PostSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostSaveResponseDto> save(@RequestBody PostSaveRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.save(requestDto));
    }
}
