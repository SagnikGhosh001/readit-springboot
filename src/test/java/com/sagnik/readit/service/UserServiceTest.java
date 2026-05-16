package com.sagnik.readit.service;

import com.sagnik.readit.dto.UserDto;
import com.sagnik.readit.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Test
    void shouldReturnLoginUser() {
        UserService mockUserService = mock(UserService.class);
        UserDto userDto = new UserDto("Sagnik");
        User user = new User("Sagnik");
        when(mockUserService.login(userDto)).thenReturn(user);
        User login = mockUserService.login(userDto);
        assertEquals(user, login);
    }
}
