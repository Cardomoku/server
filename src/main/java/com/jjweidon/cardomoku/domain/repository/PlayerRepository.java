package com.jjweidon.cardomoku.domain.repository;

import com.jjweidon.cardomoku.domain.entity.Player;
import com.jjweidon.cardomoku.domain.entity.Room;
import com.jjweidon.cardomoku.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {
    Optional<Player> findByUser(User user);
    List<Player> findByRoom(Room room);
}
