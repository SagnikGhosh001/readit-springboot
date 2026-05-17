package com.sagnik.readit.mongodbServiceImpl;

import com.sagnik.readit.entity.User;
import com.sagnik.readit.exception.BadRequestException;
import com.sagnik.readit.exception.NotFoundException;
import com.sagnik.readit.repository.UserMongoRepository;
import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import com.sagnik.readit.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (existingUser.isPresent()) return existingUser.get().toResponse(UserResponseDto::new);
        User user = new User(userRequestDto.username());
        userMongoRepository.insert(user);
        return user.toResponse(UserResponseDto::new);
    }

    @Override
    public UserResponseDto toggleSubscribe(String hostId, String userId) {
        if (hostId.equals(userId)) throw new BadRequestException("Can't Subscribe own");

        User user = userMongoRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User is not found with id %s", userId)));
        User host = userMongoRepository.findById(hostId)
                .orElseThrow(() -> new NotFoundException(String.format("Host is not found with id %s", hostId)));
        user.toggleSubscribe(host);
        userMongoRepository.save(host);
        userMongoRepository.save(user);
        return user.toResponse(UserResponseDto::new);
    }

    @Override
    public List<UserResponseDto> search(String username) {
        return userMongoRepository.findByUsernameStartingWithIgnoreCase(username)
                .stream().map(u -> u.toResponse(UserResponseDto::new))
                .toList();
    }
}
