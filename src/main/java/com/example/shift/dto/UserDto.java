// src/main/java/com/example/shift/dto/UserDto.java
package com.example.shift.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private String fullName;
    private String email;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<String> roles;
}