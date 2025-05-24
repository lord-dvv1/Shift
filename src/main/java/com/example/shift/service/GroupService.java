// src/main/java/com/example/shift/service/GroupService.java
package com.example.shift.service;

import com.example.shift.dto.GroupDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public interface GroupService {
    List<GroupDto> getAllGroups();
    Optional<GroupDto> getGroupById(Long id);
    GroupDto createGroup(GroupDto groupDto);
    Optional<GroupDto> updateGroup(Long id, GroupDto groupDto);
    boolean deleteGroup(Long id);
    Optional<GroupDto> addStudentsToGroup(Long groupId, List<Long> studentIds);
    Optional<GroupDto> removeStudentFromGroup(Long groupId, Long studentId);
}