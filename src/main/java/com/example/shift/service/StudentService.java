// src/main/java/com/example/shift/service/StudentService.java
package com.example.shift.service;

import com.example.shift.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public interface StudentService {
    List<StudentDto> getAllStudents();
    List<StudentDto> getAllActiveStudents();
    Optional<StudentDto> getStudentById(Long id);
    StudentDto createStudent(StudentDto studentDto);
    Optional<StudentDto> updateStudent(Long id, StudentDto studentDto);
    boolean deleteStudent(Long id);
    Optional<StudentDto> toggleStudentActiveStatus(Long id, boolean isActive);
    List<StudentDto> getStudentsByGroupId(Long groupId);
}