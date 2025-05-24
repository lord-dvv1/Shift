// src/main/java/com/example/shift/service/impl/DashboardServiceImpl.java
package com.example.shift.service.impl;

import com.example.shift.repository.GroupRepository;
import com.example.shift.repository.StudentRepository;
import com.example.shift.repository.TeacherRepository;
import com.example.shift.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public DashboardServiceImpl(TeacherRepository teacherRepository, StudentRepository studentRepository, GroupRepository groupRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public long getTotalTeachers() {
        return teacherRepository.count();
    }

    @Override
    public long getTotalStudents() {
        return studentRepository.count();
    }

    @Override
    public long getTotalGroups() {
        return groupRepository.count();
    }

    @Override
    public long getActiveStudents() {
        return studentRepository.countByIsActiveTrue();
    }
}