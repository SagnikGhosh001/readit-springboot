package com.sagnik.readit.config;

import com.sagnik.readit.entity.User;
import com.sagnik.readit.exception.NotFoundException;
import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import com.sagnik.readit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) throw new NotFoundException(String.format("User not found with username %s", username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .roles("USER")
                .build();
    }

    public UserResponseDto login(UserRequestDto user) {
        logger.info("Logging in user with request {}", user);
        UserResponseDto login = userService.login(user);
        logger.info("Successfully logged in user with Id {}", login.id());
        return login;
    }
}
