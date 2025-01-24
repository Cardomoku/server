package com.jjweidon.cardomoku.domain.game.repository;

import com.jjweidon.cardomoku.domain.game.entity.Game;
import com.jjweidon.cardomoku.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, String> {
    Optional<Game> findByRoom(Room room);
}
