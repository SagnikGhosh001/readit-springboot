package com.sagnik.readit.controller;

import com.sagnik.readit.config.MyUserDetailsService;
import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import com.sagnik.readit.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;
    @Value("${security.jwt.expiration-time}")
    private long EXPIRATION_TIME;

    public AuthController(JwtService jwtService, MyUserDetailsService myUserDetailsService) {
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> register(@RequestBody UserRequestDto user) {
        UserResponseDto userResponseDto = myUserDetailsService.login(user);
        String token = jwtService.generateToken(userResponseDto.username());
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofMillis(EXPIRATION_TIME))
                .build();

        return ResponseEntity.status(201)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
