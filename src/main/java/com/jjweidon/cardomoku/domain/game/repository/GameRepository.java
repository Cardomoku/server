package com.jjweidon.cardomoku.domain.game.repository;

import com.jjweidon.cardomoku.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
}
