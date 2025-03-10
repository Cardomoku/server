package com.jjweidon.cardomoku.domain.repository;

import com.jjweidon.cardomoku.domain.entity.PlayerCard;
import com.jjweidon.cardomoku.domain.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerCardRepository extends JpaRepository<PlayerCard, String> {
    List<PlayerCard> findByPlayer(Player player);
}
