// src/main/java/com/example/shift/repository/AttendanceRepository.java
package com.example.shift.repository;

import com.example.shift.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByStudentIdAndDateAndGroupId(Long studentId, LocalDate date, Long groupId);

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByGroupIdAndDate(Long groupId, LocalDate date);

    List<Attendance> findByGroupIdAndDateBetween(Long groupId, LocalDate startDate, LocalDate endDate);

     List<Attendance> findByStudentIdAndGroupId(Long studentId, Long groupId);
}