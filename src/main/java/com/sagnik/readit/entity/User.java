package com.sagnik.readit.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {
    @Id
    private String id;
    private final String username;
    private final Date createdAt;

    @JsonCreator
    public User(String username) {
        this.username = username;
        this.createdAt = new Date();
    }
}
