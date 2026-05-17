package com.sagnik.readit.controller;

import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.PostMongoRepository;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.PostRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Optional;

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
    void shouldReturnLoginUserWithStatusCode() {
        PostRequestDto postRequestDto = new PostRequestDto("title", "body", "1");
        when(userMongoRepository.findById("1")).thenReturn(Optional.of(new User("user1")));

        testClient.post()
                .uri("/api/post/")
                .body(postRequestDto)
                .exchange()
                .expectStatus().isCreated();
    }
}
