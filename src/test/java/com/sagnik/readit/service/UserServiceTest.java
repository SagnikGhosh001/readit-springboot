package com.sagnik.readit.service;

import com.sagnik.readit.dto.UserDto;
import com.sagnik.readit.entity.User;
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
        UserDto userDto = new UserDto("Sagnik");
        User user = new User("Sagnik");
        when(mockUserService.login(userDto)).thenReturn(user);
        User login = mockUserService.login(userDto);
        assertEquals(user, login);
    }
}
