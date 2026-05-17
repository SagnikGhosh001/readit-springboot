package com.sagnik.readit.service;

import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;

public interface PostService {
    PostResponseDto createPost(PostRequestDto postRequestDto);

    PostResponseDto toggleLike(String postId, String userId);
}
