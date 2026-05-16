package com.sagnik.readit.controller;

import com.sagnik.readit.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@AutoConfigureRestTestClient
public class UserControllerTest {

    @Autowired
    RestTestClient testClient;

    @Test
    void shouldReturnLoginUserWithStatusCode() {
        UserDto userDto = new UserDto("user1");
        testClient.post()
                .uri("/api/user/login")
                .body(userDto)
                .exchange()
                .expectStatus().isCreated();
    }
}
