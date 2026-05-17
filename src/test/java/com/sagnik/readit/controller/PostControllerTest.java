package com.sagnik.readit.controller;

import com.sagnik.readit.entity.Post;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.PostMongoRepository;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureRestTestClient
public class PostControllerTest {
    @Autowired
    RestTestClient testClient;

    @MockitoBean
    private UserMongoRepository userMongoRepository;

    @MockitoBean
    private PostMongoRepository postMongoRepository;

    @Test
    void shouldCreatePost() {

        PostRequestDto postRequestDto = new PostRequestDto("title", "body", "1");
        User user = new User("user1");
        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));

        PostResponseDto responseBody = testClient.post()
                .uri("/api/post/")
                .body(postRequestDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(PostResponseDto.class).getResponseBody();
        assert responseBody != null;
        assertEquals(user.toResponse(), responseBody.user());
        assertEquals("title", responseBody.title());
        assertEquals("body", responseBody.body());
        assertEquals(0, responseBody.likedBy().size());
    }

    @Test
    void shouldLikePost() {
        User user = new User("user1");
        Post post = new Post("title", "body", user);

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));
        when(postMongoRepository.findById("1")).thenReturn(Optional.of(post));
        when(postMongoRepository.save(any(Post.class))).thenReturn(post);
        
        PostResponseDto responseBody = testClient.put()
                .uri("/api/post/like/1/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(PostResponseDto.class).getResponseBody();

        assert responseBody != null;
        assertEquals(user.toResponse(), responseBody.user());
        assertEquals("title", responseBody.title());
        assertEquals("body", responseBody.body());
        assertEquals(1, responseBody.likedBy().size());
    }

    @Test
    void shouldUnlikeLikePost() {
        User user = new User("user1");
        Post post = new Post("title", "body", user);

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));
        when(postMongoRepository.findById("1")).thenReturn(Optional.of(post));
        when(postMongoRepository.save(any(Post.class))).thenReturn(post);

        testClient.put()
                .uri("/api/post/like/1/1")
                .exchange()
                .expectStatus().isOk();

        PostResponseDto responseBody = testClient.put()
                .uri("/api/post/like/1/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(PostResponseDto.class).getResponseBody();

        assert responseBody != null;
        assertEquals(user.toResponse(), responseBody.user());
        assertEquals("title", responseBody.title());
        assertEquals("body", responseBody.body());
        assertEquals(0, responseBody.likedBy().size());
    }


    @Test
    void shouldSendNotFoundIfPostIsNotPresent() {
        User user = new User("user1");

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));

        testClient.put()
                .uri("/api/post/like/1/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldSendNotFoundIfUserIsNotPresent() {
        User user = new User("user1");
        Post post = new Post("title", "body", user);

        when(postMongoRepository.findById("1")).thenReturn(Optional.of(post));

        testClient.put()
                .uri("/api/post/like/1/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}
