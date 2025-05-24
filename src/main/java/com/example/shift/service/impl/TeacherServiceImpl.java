// src/main/java/com/example/shift/service/impl/TeacherServiceImpl.java
package com.example.shift.service.impl;

import com.example.shift.dto.TeacherDto;
import com.example.shift.entity.Teacher;
import com.example.shift.repository.TeacherRepository;
import com.example.shift.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TeacherDto> getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    @Transactional
    public TeacherDto createTeacher(TeacherDto teacherDto) {
        Teacher teacher = convertToEntity(teacherDto);
        teacher = teacherRepository.save(teacher);
        return convertToDto(teacher);
    }

    @Override
    @Transactional
    public Optional<TeacherDto> updateTeacher(Long id, TeacherDto teacherDto) {
        return teacherRepository.findById(id)
                .map(existingTeacher -> {
                    existingTeacher.setPhone(teacherDto.getPhone());
                    existingTeacher.setName(teacherDto.getName());
                    existingTeacher.setDataBirthday(teacherDto.getDataBirthday());
                    existingTeacher.setGender(teacherDto.getGender());
                    Teacher updatedTeacher = teacherRepository.save(existingTeacher);
                    return convertToDto(updatedTeacher);
                });
    }

    @Override
    @Transactional
    public boolean deleteTeacher(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TeacherDto convertToDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setPhone(teacher.getPhone());
        dto.setName(teacher.getName());
        dto.setDataBirthday(teacher.getDataBirthday());
        dto.setGender(teacher.getGender());
        return dto;
    }

    private Teacher convertToEntity(TeacherDto dto) {
        Teacher teacher = new Teacher();
        if (dto.getId() != null) {
            teacher.setId(dto.getId());
        }
        teacher.setPhone(dto.getPhone());
        teacher.setName(dto.getName());
        teacher.setDataBirthday(dto.getDataBirthday());
        teacher.setGender(dto.getGender());
        return teacher;
    }
}