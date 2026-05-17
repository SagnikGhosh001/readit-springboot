package com.sagnik.readit.entity;

import com.sagnik.readit.responseDto.UserResponseDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private final String username;
    private Date createdAt;

    public User(String username) {
        this.username = username;
        this.createdAt = new Date();
    }

    public UserResponseDto toResponse() {
        return new UserResponseDto(id, username, createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
