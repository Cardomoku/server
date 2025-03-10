package com.jjweidon.cardomoku.domain.repository;

import com.jjweidon.cardomoku.domain.entity.GameBoard;
import com.jjweidon.cardomoku.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<GameBoard, String> {
    List<GameBoard> find(Room room);
}
