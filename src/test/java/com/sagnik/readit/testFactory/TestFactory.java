package com.sagnik.readit.testFactory;

import com.sagnik.readit.entity.Post;
import com.sagnik.readit.entity.User;

import java.lang.reflect.Field;

public class TestFactory {

    public static User user(String id) {
        User u = new User("user" + id);
        setId(u, id);
        return u;
    }

    public static Post post(String id, User user) {
        Post p = new Post("title", "body", user);
        setId(p, id);
        return p;
    }

    private static void setId(Object obj, String value) {
        try {
            Field field = obj.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}