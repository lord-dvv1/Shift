package com.example.shift.service;

import com.example.shift.dto.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomDto> getAllRooms();
    Optional<RoomDto> getRoomById(Long id);
    RoomDto createRoom(RoomDto roomDto);
    Optional<RoomDto> updateRoom(Long id, RoomDto roomDto);
    boolean deleteRoom(Long id);
}