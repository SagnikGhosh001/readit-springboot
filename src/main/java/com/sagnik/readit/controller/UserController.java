package com.sagnik.readit.controller;

import com.sagnik.readit.responseDto.UserResponseDto;
import com.sagnik.readit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    

    @PutMapping("/toggle-subscribe/{hostId}/{userId}")
    public ResponseEntity<UserResponseDto> toggleSubscribe(@PathVariable String hostId, @PathVariable String userId) {
        logger.info("Toggling subscribe for host Id {} by user Id {}", hostId, userId);
        UserResponseDto response = userService.toggleSubscribe(hostId, userId);
        logger.info("Successfully toggled subscribe for host Id {} by user Id {}", hostId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("search/{username}")
    public ResponseEntity<List<UserResponseDto>> searchUser(@PathVariable String username) {
        logger.info("Searching users with username {}", username);
        List<UserResponseDto> response = userService.search(username);
        logger.info("Successfully fetched {} users for username {}", response.size(), username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
