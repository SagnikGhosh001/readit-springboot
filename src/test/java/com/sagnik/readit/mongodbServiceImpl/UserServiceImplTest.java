package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserServiceImplTest {

    private UserMongoRepository mockUserRepo;

    @BeforeEach
    void beforeEach() {
        mockUserRepo = mock(UserMongoRepository.class);
    }

    @Test
    void shouldLoginNewUser() {
        UserServiceImpl userService = new UserServiceImpl(mockUserRepo);
        UserResponseDto user = userService.login(new UserRequestDto("Sagnik"));
        verify(mockUserRepo).insert(any(User.class));
        assertEquals("Sagnik", user.username());
    }

    @Test
    void shouldReturnExistingLoginUserIfUserAlreadyExists() {
        UserServiceImpl userService = new UserServiceImpl(mockUserRepo);
        UserResponseDto firstLoginResponse = userService.login(new UserRequestDto("Sagnik"));
        UserResponseDto secondLoginResponse = userService.login(new UserRequestDto("Sagnik"));
        assertEquals(firstLoginResponse.id(), secondLoginResponse.id());
    }
}
