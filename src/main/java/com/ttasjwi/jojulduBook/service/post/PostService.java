package com.ttasjwi.jojulduBook.service.post;

import com.ttasjwi.jojulduBook.domain.post.Post;
import com.ttasjwi.jojulduBook.domain.post.PostRepository;
import com.ttasjwi.jojulduBook.exception.PostNotFoundException;
import com.ttasjwi.jojulduBook.web.dto.PostResponseDto;
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

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 게시글이 없습니닷...! "+postId));
        return new PostResponseDto(post);
    }
}
