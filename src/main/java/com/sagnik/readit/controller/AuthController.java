package com.sagnik.readit.controller;

import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import com.sagnik.readit.service.JwtService;
import com.sagnik.readit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Value("${security.jwt.expiration-time}")
    private long EXPIRATION_TIME;

    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto user) {
        logger.info("Logging in user with request {}", user);
        UserResponseDto userResponseDto = userService.login(user);
        String token = jwtService.generateToken(userResponseDto.username());
        logger.info("Successfully logged in user with Id {}", userResponseDto.id());
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
