package com.sagnik.readit.entity;

import java.util.Date;

@FunctionalInterface
public interface SimpleUserProjector<T> {
    T project(String id, String username, Date createdAt);
}
