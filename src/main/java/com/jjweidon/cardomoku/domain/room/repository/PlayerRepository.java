package com.jjweidon.cardomoku.domain.room.repository;

import com.jjweidon.cardomoku.domain.room.entity.Player;
import com.jjweidon.cardomoku.domain.room.entity.Room;
import com.jjweidon.cardomoku.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {
    Optional<Player> findByRoomAndUser(Room room, User user);
}
