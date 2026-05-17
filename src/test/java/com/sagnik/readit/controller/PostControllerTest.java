package com.sagnik.readit.controller;

import com.sagnik.readit.entity.Post;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.PostMongoRepository;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.PostRequestDto;
import com.sagnik.readit.responseDto.PostResponseDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import com.sagnik.readit.testFactory.TestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;
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
        User user = TestFactory.user("1");
        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));

        PostResponseDto responseBody = testClient.post()
                .uri("/api/post/")
                .body(postRequestDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(PostResponseDto.class).getResponseBody();
        assert responseBody != null;
        assertEquals(user.toResponse(UserResponseDto::new), responseBody.user());
        assertEquals("title", responseBody.title());
        assertEquals("body", responseBody.body());
        assertEquals(0, responseBody.likedBy().size());
    }

    @Test
    void shouldLikePost() {
        User user = TestFactory.user("1");
        Post post = TestFactory.post("1", user);

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));
        when(postMongoRepository.findById("1")).thenReturn(Optional.of(post));
        when(postMongoRepository.save(any(Post.class))).thenReturn(post);

        PostResponseDto responseBody = testClient.put()
                .uri("/api/post/like/1/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(PostResponseDto.class).getResponseBody();

        assert responseBody != null;
        assertEquals(user.toResponse(UserResponseDto::new), responseBody.user());
        assertEquals("title", responseBody.title());
        assertEquals("body", responseBody.body());
        assertEquals(1, responseBody.likedBy().size());
    }

    @Test
    void shouldUnlikeLikePost() {
        User user = TestFactory.user("1");
        Post post = TestFactory.post("1", user);

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
        assertEquals(user.toResponse(UserResponseDto::new), responseBody.user());
        assertEquals("title", responseBody.title());
        assertEquals("body", responseBody.body());
        assertEquals(0, responseBody.likedBy().size());
    }


    @Test
    void shouldSendNotFoundIfPostIsNotPresent() {
        User user = TestFactory.user("1");

        when(userMongoRepository.findById("1")).thenReturn(Optional.of(user));

        testClient.put()
                .uri("/api/post/like/1/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldSendNotFoundIfUserIsNotPresent() {
        User user = TestFactory.user("1");
        Post post = TestFactory.post("title", user);

        when(postMongoRepository.findById("1")).thenReturn(Optional.of(post));

        testClient.put()
                .uri("/api/post/like/1/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldSendNotFoundIfUserIsNotPresentForGettingUserPost() {
        testClient.get()
                .uri("/api/post/1")
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void shouldSendUserUploadedPost() {
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(TestFactory.user("1")));
        when(postMongoRepository.findByUser_Id(any(String.class))).thenReturn(List.of(
                TestFactory.post("title1", TestFactory.user("1")),
                TestFactory.post("title1", TestFactory.user("1")),
                TestFactory.post("title1", TestFactory.user("1"))
        ));

        List responseBody = testClient.get()
                .uri("/api/post/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(List.class).getResponseBody();

        assert responseBody != null;
        assertEquals(3, responseBody.size());
    }

    @Test
    void shouldSendNotFoundIfUserIsNotPresentForGettingUserFeed() {
        testClient.get()
                .uri("/api/post/user-feed/1")
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void shouldSendUserFeed() {
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(TestFactory.user("1")));
        when(postMongoRepository.findFeed(any(String.class), any(List.class))).thenReturn(List.of(
                TestFactory.post("title1", TestFactory.user("1")),
                TestFactory.post("title1", TestFactory.user("1")),
                TestFactory.post("title1", TestFactory.user("1"))
        ));

        List responseBody = testClient.get()
                .uri("/api/post/user-feed/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(List.class).getResponseBody();

        assert responseBody != null;
        assertEquals(3, responseBody.size());
    }

    @Test
    void shouldDeletePost() {
        User user = TestFactory.user("1");
        Post post = TestFactory.post("1", user);
        when(postMongoRepository.findById(any(String.class))).thenReturn(Optional.of(post));
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(user));

        PostResponseDto responseBody = testClient.delete()
                .uri("/api/post/1/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(PostResponseDto.class).getResponseBody();

        assert responseBody != null;
        assertEquals("1", responseBody.id());
    }

    @Test
    void shouldNotDeletePostForOtherPostDeleteRequest() {
        User user = TestFactory.user("1");
        Post post = TestFactory.post("1", user);
        when(postMongoRepository.findById(any(String.class))).thenReturn(Optional.of(post));
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(user));

        testClient.delete()
                .uri("/api/post/1/2")
                .exchange()
                .expectStatus().isBadRequest();

    }


    @Test
    void shouldGiveNotFoundIfPostIsMissing() {
        User user = TestFactory.user("1");
        when(userMongoRepository.findById(any(String.class))).thenReturn(Optional.of(user));

        testClient.delete()
                .uri("/api/post/1/2")
                .exchange()
                .expectStatus().isNotFound();

    }


    @Test
    void shouldGiveNotFoundIfUserIsMissing() {
        User user = TestFactory.user("1");
        Post post = TestFactory.post("1", user);
        when(postMongoRepository.findById(any(String.class))).thenReturn(Optional.of(post));

        testClient.delete()
                .uri("/api/post/1/2")
                .exchange()
                .expectStatus().isNotFound();

    }
}
