// src/main/java/com/example/shift/dto/LoginResponse.java
package com.example.shift.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String accessToken;
    private String refreshToken;
    private List<String> roles;
}