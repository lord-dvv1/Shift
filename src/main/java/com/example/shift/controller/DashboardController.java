package com.example.shift.controller;

import com.example.shift.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> getDashboardStatistics() {
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("totalTeachers", dashboardService.getTotalTeachers());
        statistics.put("totalStudents", dashboardService.getTotalStudents());
        statistics.put("totalGroups", dashboardService.getTotalGroups());
        statistics.put("activeStudents", dashboardService.getActiveStudents());
        return ResponseEntity.ok(statistics);
    }
}