// src/main/java/com/example/shift/service/TeacherService.java
package com.example.shift.service;

import com.example.shift.dto.TeacherDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public interface TeacherService {
    List<TeacherDto> getAllTeachers();
    Optional<TeacherDto> getTeacherById(Long id);
    TeacherDto createTeacher(TeacherDto teacherDto);
    Optional<TeacherDto> updateTeacher(Long id, TeacherDto teacherDto);
    boolean deleteTeacher(Long id);
}