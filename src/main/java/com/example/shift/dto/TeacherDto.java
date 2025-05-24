// src/main/java/com/example/shift/dto/TeacherDto.java
package com.example.shift.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class TeacherDto {
    private Long id;
    private String phone;
    private String name;
    private LocalDate dataBirthday;
    private String gender;
      List<GroupDto> groups;
}