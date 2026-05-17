package com.sagnik.readit.service;

import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostRequestDto postRequestDto);

    PostResponseDto toggleLike(String postId, String userId);

    List<PostResponseDto> getUserUploadedPost(String userId);

    List<PostResponseDto> getUserFeed(String userId);
}
