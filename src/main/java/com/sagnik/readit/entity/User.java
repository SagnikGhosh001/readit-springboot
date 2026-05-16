package com.sagnik.readit.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private final String username;
    private final Date createdAt;

    public User(String username, Date createdAt) {
        this.username = username;
        this.createdAt = createdAt;
    }
}
