// src/main/java/com/example/shift/service/impl/GroupServiceImpl.java
package com.example.shift.service.impl;

import com.example.shift.dto.GroupDto;
import com.example.shift.dto.StudentDto;
import com.example.shift.entity.Group;
import com.example.shift.entity.Room;
import com.example.shift.entity.Student;
import com.example.shift.entity.Teacher;
import com.example.shift.repository.GroupRepository;
import com.example.shift.repository.RoomRepository;
import com.example.shift.repository.StudentRepository;
import com.example.shift.repository.TeacherRepository;
import com.example.shift.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;

    public GroupServiceImpl(GroupRepository groupRepository, TeacherRepository teacherRepository,
                            RoomRepository roomRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GroupDto> getGroupById(Long id) {
        return groupRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    @Transactional
    public GroupDto createGroup(GroupDto groupDto) {
        Group group = new Group();
        updateGroupFieldsFromDto(group, groupDto);
        group = groupRepository.save(group);
        return convertToDto(group);
    }

    @Override
    @Transactional
    public Optional<GroupDto> updateGroup(Long id, GroupDto groupDto) {
        return groupRepository.findById(id)
                .map(existingGroup -> {
                    updateGroupFieldsFromDto(existingGroup, groupDto);
                    Group updatedGroup = groupRepository.save(existingGroup);
                    return convertToDto(updatedGroup);
                });
    }

    @Override
    @Transactional
    public boolean deleteGroup(Long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Optional<GroupDto> addStudentsToGroup(Long groupId, List<Long> studentIds) {
        return groupRepository.findById(groupId)
                .map(group -> {
                    List<Student> studentsToAdd = studentRepository.findAllById(studentIds);
                    group.getStudents().addAll(studentsToAdd);
                    groupRepository.save(group);
                    return convertToDto(group);
                });
    }

    @Override
    @Transactional
    public Optional<GroupDto> removeStudentFromGroup(Long groupId, Long studentId) {
        return groupRepository.findById(groupId)
                .map(group -> {
                    group.getStudents().removeIf(s -> s.getId().equals(studentId));
                    groupRepository.save(group);
                    return convertToDto(group);
                });
    }

    private GroupDto convertToDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setCourse(group.getCourse());
        dto.setPrice(group.getPrice());
        dto.setDays(group.getDays());
        dto.setLessonStartDate(group.getLessonStartDate());
        dto.setStartTime(group.getStartTime());
        dto.setEndTime(group.getEndTime());

        if (group.getTeacher() != null) {
            dto.setTeacherId(group.getTeacher().getId());
            dto.setTeacherName(group.getTeacher().getName());
        }
        if (group.getRoom() != null) {
            dto.setRoomId(group.getRoom().getId());
            dto.setRoomName(group.getRoom().getName());
        }
        if (group.getStudents() != null && !group.getStudents().isEmpty()) {
            dto.setStudentsInGroup(group.getStudents().stream()
                    .map(s -> {
                        StudentDto studentDto = new StudentDto();
                        studentDto.setId(s.getId());
                        studentDto.setFullName(s.getFullName());
                        return studentDto;
                    })
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private void updateGroupFieldsFromDto(Group group, GroupDto dto) {
        group.setName(dto.getName());
        group.setCourse(dto.getCourse());
        group.setPrice(dto.getPrice());
        group.setDays(dto.getDays());
        group.setLessonStartDate(dto.getLessonStartDate());
        group.setStartTime(dto.getStartTime());
        group.setEndTime(dto.getEndTime());

        if (dto.getTeacherId() != null) {
            teacherRepository.findById(dto.getTeacherId())
                    .ifPresent(group::setTeacher);
        } else {
            group.setTeacher(null);
        }

        if (dto.getRoomId() != null) {
            roomRepository.findById(dto.getRoomId())
                    .ifPresent(group::setRoom);
        } else {
            group.setRoom(null);
        }

        if (dto.getStudentIds() != null) {
            Set<Student> students = new HashSet<>(studentRepository.findAllById(dto.getStudentIds()));
            group.setStudents(students);
        } else {
            group.setStudents(new HashSet<>());
        }
    }
}