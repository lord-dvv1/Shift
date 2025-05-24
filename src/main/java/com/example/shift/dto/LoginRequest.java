// src/main/java/com/example/shift/dto/LoginRequest.java
package com.example.shift.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}