package com.sagnik.readit.service;

import com.sagnik.readit.requestDto.UserRequestDto;
import com.sagnik.readit.responseDto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto login(UserRequestDto userRequestDto);

    UserResponseDto toggleSubscribe(String hostId, String userId);

    List<UserResponseDto> search(String username);
}
