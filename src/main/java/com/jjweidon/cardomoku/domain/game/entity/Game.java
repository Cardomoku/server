package com.jjweidon.cardomoku.domain.game.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jjweidon.cardomoku.domain.room.entity.Player;
import com.jjweidon.cardomoku.domain.room.entity.Room;
import com.jjweidon.cardomoku.global.entity.BaseTime;
import com.jjweidon.cardomoku.global.entity.enums.Color;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game extends BaseTime {

    @Id
    @Column(name = "game_id")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @JsonIgnore
    @OneToMany(mappedBy = "game_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Board> boards;

    @JsonIgnore
    @OneToMany(mappedBy = "game_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameHistory> histories;

    @Enumerated(EnumType.STRING)
    private Color winningTeam;
}
