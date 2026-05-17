package com.sagnik.readit.controller;

import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.UserRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

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
        testClient.post()
                .uri("/api/user/login")
                .body(userRequestDto)
                .exchange()
                .expectStatus().isCreated();
    }
}
