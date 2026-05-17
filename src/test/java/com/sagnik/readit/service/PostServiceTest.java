package com.sagnik.readit.service;

import com.sagnik.readit.entity.Post;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        User user = new User("user1");
        PostResponseDto post = new Post("title", "body", user).toResponse();
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
}
