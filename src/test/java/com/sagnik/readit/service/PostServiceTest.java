package com.sagnik.readit.service;

import com.sagnik.readit.dto.PostDto;
import com.sagnik.readit.entity.Post;
import com.sagnik.readit.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostServiceTest {
    PostService mockPostService;

    @BeforeEach
    void beforeEach() {
        mockPostService = mock(PostService.class);
    }

    @Test
    void shouldCreateTestWithUser() {
        PostDto postDto = new PostDto("title", "body", "userId");
        User user = new User("user1");
        Post post = new Post("title", "body", user);
        when(mockPostService.createPost(postDto)).thenReturn(post);

        Post newPost = mockPostService.createPost(postDto);
        assertEquals(post, newPost);
    }
}
