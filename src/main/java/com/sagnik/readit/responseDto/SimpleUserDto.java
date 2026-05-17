package com.sagnik.readit.responseDto;

import java.util.Date;

public record SimpleUserDto(String id, String username, Date createdAt) {
}
