package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.dto.UserDto;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.UserMongoRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserServiceImplTest {
    @Test
    void shouldLoginUser() {
        UserMongoRepository mockUserRepo = mock(UserMongoRepository.class);
        UserServiceImpl userService = new UserServiceImpl(mockUserRepo);
        userService.login(new UserDto("Sagnik"));
        verify(mockUserRepo).insert(any(User.class));
    }
}
