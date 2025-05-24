// src/main/java/com/example/shift/controller/AttendanceController.java
package com.example.shift.controller;

import com.example.shift.dto.AttendanceDto;
import com.example.shift.service.AttendanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AttendanceDto> recordAttendance(@RequestBody AttendanceDto attendanceDto) {
        try {
            AttendanceDto recordedAttendance = attendanceService.recordAttendance(attendanceDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(recordedAttendance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AttendanceDto> getAttendanceById(@PathVariable Long id) {
        Optional<AttendanceDto> attendanceDto = attendanceService.getAttendanceById(id);
        if (attendanceDto.isPresent()) {
            return ResponseEntity.ok(attendanceDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByStudentId(@PathVariable Long studentId) {
        List<AttendanceDto> attendanceRecords = attendanceService.getAttendanceByStudentId(studentId);
        return ResponseEntity.ok(attendanceRecords);
    }

    @GetMapping("/group/{groupId}/date/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByGroupIdAndDate(
            @PathVariable Long groupId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AttendanceDto> attendanceRecords = attendanceService.getAttendanceByGroupIdAndDate(groupId, date);
        return ResponseEntity.ok(attendanceRecords);
    }

    @GetMapping("/group/{groupId}/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<LocalDate, Map<String, Boolean>>> getGroupAttendanceSummary(
            @PathVariable Long groupId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<LocalDate, Map<String, Boolean>> summary = attendanceService.getGroupAttendanceSummary(groupId, startDate, endDate);
        return ResponseEntity.ok(summary);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AttendanceDto> updateAttendanceStatus(
            @PathVariable Long id,
            @RequestParam Boolean present,
            @RequestParam(required = false) String comment) {
        Optional<AttendanceDto> updatedAttendance = attendanceService.updateAttendanceStatus(id, present, comment);
        if (updatedAttendance.isPresent()) {
            return ResponseEntity.ok(updatedAttendance.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AttendanceDto>> getAllAttendance() {
        List<AttendanceDto> allAttendance = attendanceService.getAllAttendance();
        return ResponseEntity.ok(allAttendance);
    }
}