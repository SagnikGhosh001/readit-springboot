package com.sagnik.readit.responseDto;

import java.util.Date;

public record PostResponseDto(String id, String title, String body, Date createdAt, UserResponseDto user) {
}
