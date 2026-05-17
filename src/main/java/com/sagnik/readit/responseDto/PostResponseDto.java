package com.sagnik.readit.responseDto;

import java.util.Date;
import java.util.List;

public record PostResponseDto(String id, String title, String body, Date createdAt, List<UserResponseDto> likedBy,
                              UserResponseDto user) {
}
