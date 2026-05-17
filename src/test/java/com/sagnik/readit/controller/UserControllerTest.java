package com.sagnik.readit.controller;

import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import com.sagnik.readit.testFactory.TestFactory;
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
public class UserControllerTest {

    @Autowired
    RestTestClient testClient;

    @MockitoBean
    private UserMongoRepository userMongoRepository;

    @Test
    void shouldReturnLoginUserWithStatusCode() {
        UserRequestDto userRequestDto = new UserRequestDto("sagnik");
        UserResponseDto responseBody = testClient.post()
                .uri("/api/user/login")
                .body(userRequestDto)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(UserResponseDto.class).getResponseBody();

        assert responseBody != null;
        assertEquals("sagnik", responseBody.username());
    }

    @Test
    void shouldSubscribe() {
        UserRequestDto userRequestDto = new UserRequestDto("sagnik");
        User user = TestFactory.user("user");
        User host = TestFactory.user("host");

        when(userMongoRepository.findById("host")).thenReturn(Optional.of(host));
        when(userMongoRepository.findById("user")).thenReturn(Optional.of(user));
        when(userMongoRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto responseBody = testClient.put()
                .uri("/api/user/toggle-subscribe/host/user")
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserResponseDto.class).getResponseBody();

        assert responseBody != null;
        assertEquals(0, responseBody.subscribers().size());
        assertEquals(1, responseBody.subscribed().size());
        assertEquals(1, host.toResponse().subscribers().size());
        assertEquals(0, host.toResponse().subscribed().size());
    }

    @Test
    void shouldGiveNotFoundIfHostIsMissing() {
        User user = TestFactory.user("1");

        when(userMongoRepository.findById("user")).thenReturn(Optional.of(user));
        when(userMongoRepository.save(any(User.class))).thenReturn(user);

        testClient.put()
                .uri("/api/user/toggle-subscribe/host/user")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldGiveNotFoundIfUserIsMissing() {
        User host = TestFactory.user("1");

        when(userMongoRepository.findById("host")).thenReturn(Optional.of(host));

        testClient.put()
                .uri("/api/user/toggle-subscribe/host/user")
                .exchange()
                .expectStatus().isNotFound();

    }

}
