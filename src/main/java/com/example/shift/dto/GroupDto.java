// src/main/java/com/example/shift/dto/GroupDto.java
package com.example.shift.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class GroupDto {
    private Long id;
    private String name;
    private String course;
    private Double price;

    private Long teacherId;
    private String teacherName;

    private Long roomId;
    private String roomName;

    private String days;
    private LocalDate lessonStartDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private List<Long> studentIds;
    private List<StudentDto> studentsInGroup;
}