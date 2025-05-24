// src/main/java/com/example/shift/service/impl/StudentServiceImpl.java
package com.example.shift.service.impl;

import com.example.shift.dto.StudentDto;
import com.example.shift.entity.Student;
import com.example.shift.repository.StudentRepository;
import com.example.shift.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDto> getAllActiveStudents() {
        return studentRepository.findByIsActiveTrueAndIsDeletedFalse().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDto> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    public List<StudentDto> getStudentsByGroupId(Long groupId) {
        return studentRepository.findByGroups_Id(groupId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = convertToEntity(studentDto);
        student = studentRepository.save(student);
        return convertToDto(student);
    }

    @Override
    @Transactional
    public Optional<StudentDto> updateStudent(Long id, StudentDto studentDto) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setFullName(studentDto.getFullName());
                    existingStudent.setEmail(studentDto.getEmail());
                    existingStudent.setPhone(studentDto.getPhone());
                    existingStudent.setDataOfBirthday(studentDto.getDataOfBirthday());
                    existingStudent.setIsActive(studentDto.getIsActive() != null ? studentDto.getIsActive() : existingStudent.getIsActive());
                    existingStudent.setIsDeleted(studentDto.getIsDeleted() != null ? studentDto.getIsDeleted() : existingStudent.getIsDeleted());
                    existingStudent.setGender(studentDto.getGender());
                    existingStudent.setComment(studentDto.getComment());
                    Student updatedStudent = studentRepository.save(existingStudent);
                    return convertToDto(updatedStudent);
                });
    }

    @Override
    @Transactional
    public boolean deleteStudent(Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setIsDeleted(true);
                    studentRepository.save(student);
                    return true;
                }).orElse(false);
    }

    @Override
    @Transactional
    public Optional<StudentDto> toggleStudentActiveStatus(Long id, boolean isActive) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setIsActive(isActive);
                    studentRepository.save(student);
                    return convertToDto(student);
                });
    }

    private StudentDto convertToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setFullName(student.getFullName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setDataOfBirthday(student.getDataOfBirthday());
        dto.setIsActive(student.getIsActive());
        dto.setIsDeleted(student.getIsDeleted());
        dto.setGender(student.getGender());
        dto.setComment(student.getComment());
        return dto;
    }

    private Student convertToEntity(StudentDto dto) {
        Student student = new Student();
        if (dto.getId() != null) {
            student.setId(dto.getId());
        }
        student.setFullName(dto.getFullName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDataOfBirthday(dto.getDataOfBirthday());
        student.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        student.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);
        student.setGender(dto.getGender());
        student.setComment(dto.getComment());
        return student;
    }
}