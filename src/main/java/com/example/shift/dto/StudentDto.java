// src/main/java/com/example/shift/dto/StudentDto.java
package com.example.shift.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class StudentDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dataOfBirthday;
    private Boolean isActive;
    private Boolean isDeleted;
    private String gender;
    private String comment;
     List<PaymentDto> payments;
    List<GroupDto> groups;
}