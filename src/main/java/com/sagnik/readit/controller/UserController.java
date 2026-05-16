package com.sagnik.readit.controller;

import com.sagnik.readit.dto.UserDto;
import com.sagnik.readit.entity.User;
import com.sagnik.readit.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDto userDto) {
        User login = userService.login(userDto);
        return new ResponseEntity<>(login, HttpStatus.CREATED);
    }
}
