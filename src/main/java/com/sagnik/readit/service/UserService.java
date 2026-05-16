package com.sagnik.readit.service;

import com.sagnik.readit.dto.UserDto;
import com.sagnik.readit.entity.User;

public interface UserService {
    User login(UserDto userDto);
}
