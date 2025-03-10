package com.jjweidon.cardomoku.domain.repository;

import com.jjweidon.cardomoku.domain.entity.enums.GameStatus;
import com.jjweidon.cardomoku.domain.entity.Room;
import com.jjweidon.cardomoku.domain.entity.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findByCode(String code);

    Optional<Room> findFirstByRoomTypeAndStatus(RoomType roomType, GameStatus status);

    boolean existsByCode(String code);
}