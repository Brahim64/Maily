package com.api.back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.back.model.Room;
import com.api.back.service.RoomService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @GetMapping("user/{userId}")
    public ResponseEntity<?> getRoomsByUserId(@RequestParam String userId) {
        try {
            return ResponseEntity.ok(roomService.getRoomsByMemberId(userId));
        } catch (Exception e) {
            return ResponseEntity
                .status(500)
                .body("Error retrieving rooms: " + e.getMessage());
        }
    }

    @GetMapping("room/roomId")
    public ResponseEntity<Room> getRoomById(@RequestParam String roomId) {
        Room room = roomService.getRoomById(roomId);
        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    

}
