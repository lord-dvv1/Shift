package com.example.shift.service.impl;

import com.example.shift.dto.RoomDto;
import com.example.shift.entity.Room;
import com.example.shift.repository.RoomRepository;
import com.example.shift.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoomDto> getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    @Transactional
    public RoomDto createRoom(RoomDto roomDto) {
        Room room = convertToEntity(roomDto);
        room.setId(null);
        Room savedRoom = roomRepository.save(room);
        return convertToDto(savedRoom);
    }

    @Override
    @Transactional
    public Optional<RoomDto> updateRoom(Long id, RoomDto roomDto) {
        return roomRepository.findById(id)
                .map(existingRoom -> {
                    existingRoom.setName(roomDto.getName());
                    Room updatedRoom = roomRepository.save(existingRoom);
                    return convertToDto(updatedRoom);
                });
    }

    @Override
    @Transactional
    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private RoomDto convertToDto(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setName(room.getName());
        return dto;
    }

    private Room convertToEntity(RoomDto roomDto) {
        Room room = new Room();
        room.setName(roomDto.getName());
        return room;
    }
}