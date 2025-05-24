// src/main/java/com/example/shift/controller/GroupController.java
package com.example.shift.controller;

import com.example.shift.dto.GroupDto;
import com.example.shift.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // Для addStudentsToGroup и removeStudentFromGroup

@RestController
@RequestMapping("/api/admin/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        List<GroupDto> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        GroupDto createdGroup = groupService.createGroup(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable Long id, @RequestBody GroupDto groupDto) {
        return groupService.updateGroup(id, groupDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        if (groupService.deleteGroup(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{groupId}/students")
    public ResponseEntity<GroupDto> addStudentsToGroup(@PathVariable Long groupId, @RequestBody Map<String, List<Long>> requestBody) {
        List<Long> studentIds = requestBody.get("studentIds");
        if (studentIds == null) {
            return ResponseEntity.badRequest().build();
        }
        return groupService.addStudentsToGroup(groupId, studentIds)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{groupId}/students/{studentId}")
    public ResponseEntity<GroupDto> removeStudentFromGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        return groupService.removeStudentFromGroup(groupId, studentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}