package com.sagnik.readit.entity;

import com.sagnik.readit.responseDto.SimpleUserDto;

import java.util.Date;
import java.util.List;

@FunctionalInterface
public interface UserProjector<T> {
    T project(String id, String username, Date createdAt, List<SimpleUserDto> subscribers, List<SimpleUserDto> subscribed);
}
