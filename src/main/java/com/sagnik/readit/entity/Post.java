package com.sagnik.readit.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("feed")
public class Post {
    @Id
    private String Id;
    private final String title;
    private final String body;
    private final Date createdAt;
    private final User user;

    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
        this.createdAt = new Date();
    }

}
