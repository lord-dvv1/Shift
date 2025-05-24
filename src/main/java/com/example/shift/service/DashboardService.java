// src/main/java/com/example/shift/service/DashboardService.java
package com.example.shift.service;

import org.springframework.stereotype.Service;

@Service

public interface DashboardService {
    long getTotalTeachers();
    long getTotalStudents();
    long getTotalGroups();
    long getActiveStudents();
}