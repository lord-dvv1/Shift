// src/main/java/com/example/shift/service/impl/AttendanceServiceImpl.java
package com.example.shift.service.impl;

import com.example.shift.dto.AttendanceDto;
import com.example.shift.entity.Attendance;
import com.example.shift.entity.Group;
import com.example.shift.entity.Student;
import com.example.shift.repository.AttendanceRepository;
import com.example.shift.repository.GroupRepository;
import com.example.shift.repository.StudentRepository;
import com.example.shift.service.AttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                 StudentRepository studentRepository,
                                 GroupRepository groupRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public AttendanceDto recordAttendance(AttendanceDto attendanceDto) {
        Student student = studentRepository.findById(attendanceDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + attendanceDto.getStudentId()));

        Optional<Attendance> existingAttendance = attendanceRepository.findByStudentIdAndDateAndGroupId(
                attendanceDto.getStudentId(), attendanceDto.getDate(), attendanceDto.getGroupId()
        );

        if (existingAttendance.isPresent()) {
            throw new IllegalArgumentException("Attendance already recorded for student " + attendanceDto.getStudentId() +
                    " in group " + attendanceDto.getGroupId() +
                    " on date " + attendanceDto.getDate());
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setDate(attendanceDto.getDate());
        attendance.setAttendanceTime(attendanceDto.getAttendanceTime());
        attendance.setPresent(attendanceDto.getPresent());
        attendance.setComment(attendanceDto.getComment());

        if (attendanceDto.getGroupId() != null) {
            Group group = groupRepository.findById(attendanceDto.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + attendanceDto.getGroupId()));
            attendance.setGroup(group);
        } else {
            throw new IllegalArgumentException("Group ID must be provided for attendance record.");
        }


        Attendance savedAttendance = attendanceRepository.save(attendance);
        return convertToDto(savedAttendance);
    }

    @Override
    public Optional<AttendanceDto> getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    public List<AttendanceDto> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDto> getAttendanceByGroupIdAndDate(Long groupId, LocalDate date) {
        return attendanceRepository.findByGroupIdAndDate(groupId, date).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Map<LocalDate, Map<String, Boolean>> getGroupAttendanceSummary(Long groupId, LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendanceRecords = attendanceRepository.findByGroupIdAndDateBetween(groupId, startDate, endDate);

        return attendanceRecords.stream()
                .collect(Collectors.groupingBy(
                        Attendance::getDate,
                        Collectors.toMap(
                                att -> att.getStudent() != null ? att.getStudent().getFullName() : "Unknown Student",
                                Attendance::getPresent,
                                (existing, replacement) -> existing
                        )
                ));
    }

    @Override
    @Transactional
    public Optional<AttendanceDto> updateAttendanceStatus(Long id, Boolean present, String comment) {
        return attendanceRepository.findById(id)
                .map(attendance -> {
                    attendance.setPresent(present);
                    attendance.setComment(comment);
                    Attendance updatedAttendance = attendanceRepository.save(attendance);
                    return convertToDto(updatedAttendance);
                });
    }

    @Override
    public List<AttendanceDto> getAllAttendance() {
        return attendanceRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AttendanceDto convertToDto(Attendance attendance) {
        AttendanceDto dto = new AttendanceDto();
        dto.setId(attendance.getId());
        dto.setDate(attendance.getDate());
        dto.setAttendanceTime(attendance.getAttendanceTime()); // Добавлено
        dto.setPresent(attendance.getPresent());
        dto.setComment(attendance.getComment());
        if (attendance.getStudent() != null) {
            dto.setStudentId(attendance.getStudent().getId());
            dto.setStudentFullName(attendance.getStudent().getFullName());
        }
        if (attendance.getGroup() != null) {
            dto.setGroupId(attendance.getGroup().getId());
            dto.setGroupName(attendance.getGroup().getName());
        }
        return dto;
    }
}