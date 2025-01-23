package com.jjweidon.cardomoku.domain.room.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jjweidon.cardomoku.domain.game.entity.enums.GameStatus;
import com.jjweidon.cardomoku.domain.room.entity.enums.RoomType;
import com.jjweidon.cardomoku.global.entity.BaseTime;
import com.jjweidon.cardomoku.global.entity.enums.Color;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseTime {

    @Id
    @Column(name = "room_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(unique = true, nullable = false)
    private String code;

    @Builder.Default
    private GameStatus status = GameStatus.NOT_STARTED;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "room_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();;

    private int entranceCoin;

    private int totalCoin;

    ///// 도메인 메서드 /////

    public void addPlayer(Player player) {
        if (players.size() >= roomType.getCapacity()) {
            throw new IllegalStateException("이 방은 이미 최대 플레이어 수에 도달했습니다.");
        }
        if (player.getUser().getCoin() < entranceCoin) {
            throw new IllegalStateException("코인이 부족합니다.");
        }
        player.getUser().loseCoin(entranceCoin);
        this.players.add(player);
        recalculateTotalCoin(entranceCoin);
        player.setColor(determinePlayerColor());
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        resetPlayerColors();
    }

    private void recalculateTotalCoin(int entranceCoin) {
        this.totalCoin += entranceCoin;
    }

    private Color determinePlayerColor() {
        int index = players.size();
        return switch (roomType) {
            case PVP -> index == 0 ? Color.RED : Color.BLUE;
            case PVPVP -> index == 0 ? Color.RED : (index == 1 ? Color.BLUE : Color.GREEN);
            case TVT -> index % 2 == 0 ? Color.RED : Color.BLUE;
        };
    }

    private void resetPlayerColors() {
        for (Player player : players) {
            player.setColor(determinePlayerColor());
        }
    }
}