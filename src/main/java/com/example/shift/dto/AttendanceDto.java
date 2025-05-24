
package com.example.shift.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private Long id;
    private Long studentId;
    private String studentFullName;
    private Long groupId;
    private String groupName;
    private LocalDate date;
    private LocalTime attendanceTime;
    private Boolean present;
    private String comment;
}