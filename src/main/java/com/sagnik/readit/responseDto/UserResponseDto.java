package com.sagnik.readit.responseDto;

import java.util.Date;
import java.util.List;

public record UserResponseDto(String id, String username, Date createdAt, List<SimpleUserDto> subscribers,
                              List<SimpleUserDto> subscribed) {
}
