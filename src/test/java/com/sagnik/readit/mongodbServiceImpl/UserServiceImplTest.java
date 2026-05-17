package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.entity.User;
import com.sagnik.readit.exception.NotFoundException;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import com.sagnik.readit.testFactory.TestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        assertEquals(firstLoginResponse.username(), secondLoginResponse.username());
    }

    @Test
    void shouldSubscribeToHostIfNotSubscribed() {
        UserServiceImpl userService = new UserServiceImpl(mockUserRepo);
        User host = TestFactory.user("hostId");
        User user = TestFactory.user("userId");

        when(mockUserRepo.findById("hostId")).thenReturn(Optional.of(host));
        when(mockUserRepo.findById("userId")).thenReturn(Optional.of(user));
        UserResponseDto userResponseDto = userService.toggleSubscribe("hostId", "userId");
        assertEquals(1, userResponseDto.subscribed().size());
        assertEquals(0, userResponseDto.subscribers().size());

        assertEquals(0, host.toResponse(UserResponseDto::new).subscribed().size());
        assertEquals(1, host.toResponse(UserResponseDto::new).subscribers().size());
    }

    @Test
    void shouldRemoveSubscribeToHostIfAlreadySubscribed() {
        UserServiceImpl userService = new UserServiceImpl(mockUserRepo);
        User host = TestFactory.user("hostId");
        User user = TestFactory.user("userId");

        when(mockUserRepo.findById("hostId")).thenReturn(Optional.of(host));
        when(mockUserRepo.findById("userId")).thenReturn(Optional.of(user));
        UserResponseDto userResponseDto1 = userService.toggleSubscribe("hostId", "userId");
        assertEquals(1, userResponseDto1.subscribed().size());
        assertEquals(0, userResponseDto1.subscribers().size());

        assertEquals(0, host.toResponse(UserResponseDto::new).subscribed().size());
        assertEquals(1, host.toResponse(UserResponseDto::new).subscribers().size());
        UserResponseDto userResponseDto2 = userService.toggleSubscribe("hostId", "userId");

        assertEquals(0, userResponseDto2.subscribed().size());
        assertEquals(0, userResponseDto2.subscribers().size());

        assertEquals(0, host.toResponse(UserResponseDto::new).subscribed().size());
        assertEquals(0, host.toResponse(UserResponseDto::new).subscribers().size());
    }

    @Test
    void shouldThrowErrorIfHostIsNotPresent() {
        UserServiceImpl userService = new UserServiceImpl(mockUserRepo);
        User user = TestFactory.user("userId");

        when(mockUserRepo.findById("userId")).thenReturn(Optional.of(user));
        assertThrows(NotFoundException.class, () -> userService.toggleSubscribe("hostId", "userId"));
    }

    @Test
    void shouldThrowErrorIfUserIsNotPresent() {
        UserServiceImpl userService = new UserServiceImpl(mockUserRepo);
        User host = TestFactory.user("hostId");
        when(mockUserRepo.findById("hostId")).thenReturn(Optional.of(host));

        assertThrows(NotFoundException.class, () -> userService.toggleSubscribe("hostId", "userId"));
    }

    @Test
    void shouldGiveSearchedUser() {
        UserServiceImpl userService = new UserServiceImpl(mockUserRepo);
        when(mockUserRepo.findByUsernameStartingWithIgnoreCase(any(String.class))).thenReturn(List.of(
                TestFactory.user("1"),
                TestFactory.user("2"),
                TestFactory.user("3")
        ));

        List<UserResponseDto> users = userService.search("us");
        assertEquals(3, users.size());
    }

    @Test
    void shouldGiveEmptyArrayIfNoUserPresentWithUserName() {
        UserServiceImpl userService = new UserServiceImpl(mockUserRepo);
        when(mockUserRepo.findByUsernameStartingWithIgnoreCase(any(String.class))).thenReturn(List.of());

        List<UserResponseDto> users = userService.search("us");
        assertEquals(0, users.size());
    }
}
