package com.api.back.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.api.back.model.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
    @Query("{ 'members': ?0 }")
    List<Room> findByMemberId(String userId);

}
