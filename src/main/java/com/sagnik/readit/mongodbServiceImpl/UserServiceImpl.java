package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.dto.UserDto;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMongoRepository userMongoRepository;

    public UserServiceImpl(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    @Override
    public User login(UserDto userDto) {
        User user = new User(userDto.username());
        userMongoRepository.insert(user);
        return user;
    }
}
