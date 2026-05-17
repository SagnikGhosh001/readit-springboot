package com.sagnik.readit.entity;

import com.sagnik.readit.responseDto.UserResponseDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document("feed")
public class Post {
    @Id
    private String id;
    private final String title;
    private final String body;
    private final Date createdAt;
    private final User user;
    private List<User> likedBy;

    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
        this.createdAt = new Date();
        likedBy = new ArrayList<>();
    }

    public <T> T toResponse(PostProjector<T> projector) {
        List<UserResponseDto> likedByResponseDtoList = likedBy.stream().map(u -> u.toResponse(UserResponseDto::new)).toList();
        return projector.project(id, title, body, createdAt, likedByResponseDtoList, user.toResponse(UserResponseDto::new));
    }

    public Post toggleLike(User user) {
        if (likedBy.contains(user)) {
            likedBy.remove(user);
            return this;
        }

        likedBy.add(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", likedBy=" + likedBy +
                '}';
    }

    public boolean isUserPost(String userId) {
        return user.isIdEqual(userId);
    }
}
