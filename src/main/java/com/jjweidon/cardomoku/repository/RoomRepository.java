package com.jjweidon.cardomoku.repository;

import com.jjweidon.cardomoku.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {
}
