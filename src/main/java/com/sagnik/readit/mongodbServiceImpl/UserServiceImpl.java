package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import com.sagnik.readit.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMongoRepository userMongoRepository;

    public UserServiceImpl(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    @Override
    public UserResponseDto login(UserRequestDto userRequestDto) {
        Optional<User> existingUser = userMongoRepository.findByUsername(userRequestDto.username());
        if (existingUser.isPresent()) return existingUser.get().toResponse();
        User user = new User(userRequestDto.username());
        userMongoRepository.insert(user);
        return user.toResponse();
    }
}
