package com.api.back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.back.model.Room;
import com.api.back.repository.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRoomsByMemberId(String userId) {
        return roomRepository.findByMemberId(userId);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
    public Room getRoomById(String roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

}
