package com.jjweidon.cardomoku.domain.room.repository;

import com.jjweidon.cardomoku.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {
}
