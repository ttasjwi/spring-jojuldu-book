package com.ttasjwi.jojulduBook.service.post;

import com.ttasjwi.jojulduBook.domain.post.Post;
import com.ttasjwi.jojulduBook.domain.post.PostRepository;
import com.ttasjwi.jojulduBook.web.dto.PostSaveRequestDto;
import com.ttasjwi.jojulduBook.web.dto.PostSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostSaveResponseDto save(PostSaveRequestDto requestDto) {
        Post savePost = requestDto.toEntity();
        postRepository.save(savePost);
        return new PostSaveResponseDto(savePost.getId());
    }
}
