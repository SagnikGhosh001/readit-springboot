package com.sagnik.readit.entity;

import com.sagnik.readit.responseDto.SimpleUserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private final String username;
    private Date createdAt;
    @DBRef(lazy = true)
    private List<User> subscribers;
    @DBRef(lazy = true)
    private List<User> subscribed;

    public User(String username) {
        this.username = username;
        this.createdAt = new Date();
        this.subscribers = new ArrayList<>();
        this.subscribed = new ArrayList<>();
    }

    public <T> T toResponse(UserProjector<T> projector) {
        List<SimpleUserDto> subs = subscribers.stream()
                .map(u -> u.toSimple(SimpleUserDto::new)).toList();
        List<SimpleUserDto> subd = subscribed.stream()
                .map(u -> u.toSimple(SimpleUserDto::new)).toList();

        return projector.project(id, username, createdAt, subs, subd);
    }

    private <T> T toSimple(SimpleUserProjector<T> projector) {
        return projector.project(id, username, createdAt);
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

    public void toggleSubscribe(User host) {
        if (this.subscribed.contains(host)) {
            this.subscribed.remove(host);
            host.subscribers.remove(this);
            return;
        }

        host.subscribers.add(this);
        this.subscribed.add(host);
    }

    public boolean isIdEqual(String userId) {
        return Objects.equals(id, userId);
    }

    public List<String> subscribedIds() {
        return subscribed.stream().map(u -> u.id).toList();
    }
}
