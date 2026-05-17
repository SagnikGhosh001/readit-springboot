package com.sagnik.readit.entity;

import com.sagnik.readit.responseDto.PostResponseDto;
import com.sagnik.readit.responseDto.UserResponseDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("feed")
public class Post {
    @Id
    private String id;
    private final String title;
    private final String body;
    private final Date createdAt;
    private final User user;
    private final List<User> likedBy;

    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
        this.createdAt = new Date();
        likedBy = new ArrayList<>();
    }

    public PostResponseDto toResponse() {
        List<UserResponseDto> likedByResponseDtoList = likedBy.stream().map(User::toResponse).toList();
        return new PostResponseDto(id, title, body, createdAt, likedByResponseDtoList, user.toResponse());
    }

    public void toggleLike(User user) {
        if (likedBy.contains(user)) {
            likedBy.remove(user);
            return;
        }

        likedBy.add(user);
    }
}
