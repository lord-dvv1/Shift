package com.example.shift.service;

import com.example.shift.dto.UserDto;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> getUserByUsername(String username);
    UserDto createUser(UserDto userDto);
}