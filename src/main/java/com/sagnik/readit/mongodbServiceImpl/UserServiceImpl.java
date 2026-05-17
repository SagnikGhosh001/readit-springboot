package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.dto.UserDto;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.UserMongoRepository;
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
    public User login(UserDto userDto) {
        Optional<User> existingUser = userMongoRepository.findByUsername(userDto.username());
        if (existingUser.isPresent()) return existingUser.get();
        User user = new User(userDto.username());
        userMongoRepository.insert(user);
        return user;
    }
}
