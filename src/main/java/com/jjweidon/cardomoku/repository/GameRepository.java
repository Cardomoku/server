package com.jjweidon.cardomoku.repository;

import com.jjweidon.cardomoku.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
}
