package com.sagnik.readit.entity;

import com.sagnik.readit.responseDto.UserResponseDto;

import java.util.Date;
import java.util.List;

@FunctionalInterface
public interface PostProjector<T> {
    T project(String id, String title, String body, Date createdAt, List<UserResponseDto> likedBy, UserResponseDto user);
}
