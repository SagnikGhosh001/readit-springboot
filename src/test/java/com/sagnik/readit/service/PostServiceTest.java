package com.sagnik.readit.service;

import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PostServiceTest {
    PostService mockPostService;

    @BeforeEach
    void beforeEach() {
        mockPostService = mock(PostService.class);
    }

    @Test
    void shouldCreatePostWithUser() {
        PostRequestDto postRequestDto = new PostRequestDto("title", "body", "userId");
        UserResponseDto user = new UserResponseDto(null, "Sagnik", new Date(), List.of(), List.of());
        PostResponseDto post = new PostResponseDto("1", "title", "body", new Date(), List.of(), user);
        when(mockPostService.createPost(postRequestDto)).thenReturn(post);

        PostResponseDto newPost = mockPostService.createPost(postRequestDto);
        assertEquals(post, newPost);
    }

    @Test
    void shouldCallToggleLike() {
        mockPostService.toggleLike("postId", "userId");
        verify(mockPostService).toggleLike("postId", "userId");
    }

    @Test
    void shouldCallGetUserUploadedPost() {
        mockPostService.getUserUploadedPost("postId");
        verify(mockPostService).getUserUploadedPost("postId");
    }

    @Test
    void shouldCallGetUserFeed() {
        mockPostService.getUserFeed("postId");
        verify(mockPostService).getUserFeed("postId");
    }

    @Test
    void shouldCallDeletePost() {
        mockPostService.deletePost("postId", "userId");
        verify(mockPostService).deletePost("postId", "userId");
    }
}
