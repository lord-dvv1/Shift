package com.example.shift.service;

import com.example.shift.dto.UserDto;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> getUserByPhone(String phone);
    UserDto createUser(UserDto userDto);
}