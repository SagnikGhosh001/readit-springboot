package com.sagnik.readit.service;

import com.sagnik.readit.entity.User;
import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    UserService mockUserService;

    @BeforeEach
    void beforeEach() {
        mockUserService = mock(UserService.class);
    }

    @Test
    void shouldReturnLoginUser() {
        UserRequestDto userRequestDto = new UserRequestDto("Sagnik");
        UserResponseDto user = new User("Sagnik").toResponse();
        when(mockUserService.login(userRequestDto)).thenReturn(user);
        UserResponseDto loginResponse = mockUserService.login(userRequestDto);
        assertEquals(user, loginResponse);
    }
}
