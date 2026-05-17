package com.sagnik.readit.service;

import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;

public interface UserService {
    UserResponseDto login(UserRequestDto userRequestDto);
}
