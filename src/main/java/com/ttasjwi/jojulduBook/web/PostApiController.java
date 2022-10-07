package com.ttasjwi.jojulduBook.web;

import com.ttasjwi.jojulduBook.service.post.PostService;
import com.ttasjwi.jojulduBook.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostSaveResponseDto> save(@RequestBody PostSaveRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.save(requestDto));
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable("id") Long postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.findById(postId));
    }

    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostUpdateResponseDto> update(
            @PathVariable("id") Long postId,
            @RequestBody PostUpdateRequestDto requestDto) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.update(postId, requestDto));
    }
}
