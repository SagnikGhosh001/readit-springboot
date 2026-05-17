package com.sagnik.readit.entity;

import com.sagnik.readit.responseDto.UserResponseDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private final String username;
    private final Date createdAt;

    public User(String username) {
        this.username = username;
        this.createdAt = new Date();
    }

    public UserResponseDto toResponse() {
        return new UserResponseDto(id, username, createdAt);
    }
}
