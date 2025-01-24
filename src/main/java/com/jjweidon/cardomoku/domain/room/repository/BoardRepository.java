package com.jjweidon.cardomoku.domain.room.repository;

import com.jjweidon.cardomoku.domain.game.entity.Board;
import com.jjweidon.cardomoku.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, String> {
    List<Board> findByGame(Game game);
}
