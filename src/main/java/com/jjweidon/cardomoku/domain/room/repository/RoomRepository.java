package com.jjweidon.cardomoku.domain.room.repository;

import com.jjweidon.cardomoku.domain.game.entity.enums.GameStatus;
import com.jjweidon.cardomoku.domain.room.entity.Room;
import com.jjweidon.cardomoku.domain.room.entity.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findByCode(String code);

    Optional<Room> findFirstByRoomTypeAndStatus(RoomType roomType, GameStatus status);

    boolean existsByCode(String code);
}