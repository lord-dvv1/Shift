// src/main/java/com/example/shift/service/AttendanceService.java
package com.example.shift.service;

import com.example.shift.dto.AttendanceDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AttendanceService {
    AttendanceDto recordAttendance(AttendanceDto attendanceDto);
    Optional<AttendanceDto> getAttendanceById(Long id);
    List<AttendanceDto> getAttendanceByStudentId(Long studentId);
    List<AttendanceDto> getAttendanceByGroupIdAndDate(Long groupId, LocalDate date);
    Map<LocalDate, Map<String, Boolean>> getGroupAttendanceSummary(Long groupId, LocalDate startDate, LocalDate endDate);
    Optional<AttendanceDto> updateAttendanceStatus(Long id, Boolean present, String comment);
    List<AttendanceDto> getAllAttendance();
}