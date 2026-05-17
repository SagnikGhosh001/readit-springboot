package com.sagnik.readit.service;

import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    UserService mockUserService;

    @BeforeEach
    void beforeEach() {
        mockUserService = mock(UserService.class);
    }

    @Test
    void shouldReturnLoginUser() {
        UserRequestDto userRequestDto = new UserRequestDto("Sagnik");
        UserResponseDto user = new UserResponseDto(null, "Sagnik", new Date(), List.of(), List.of());
        when(mockUserService.login(userRequestDto)).thenReturn(user);
        UserResponseDto loginResponse = mockUserService.login(userRequestDto);
        assertEquals(user, loginResponse);
    }

    @Test
    void shouldSubscribeHost() {
        mockUserService.toggleSubscribe("1", "1");
        verify(mockUserService).toggleSubscribe("1", "1");
    }

    @Test
    void shouldCallSearch() {
        mockUserService.search("username");
        verify(mockUserService).search("username");
    }
}
