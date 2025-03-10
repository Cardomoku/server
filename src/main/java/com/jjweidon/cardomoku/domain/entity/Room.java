package com.jjweidon.cardomoku.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jjweidon.cardomoku.domain.entity.enums.GameStatus;
import com.jjweidon.cardomoku.domain.entity.enums.RoomType;
import com.jjweidon.cardomoku.global.entity.BaseTime;
import com.jjweidon.cardomoku.domain.entity.enums.Team;
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
    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.NOT_STARTED;

    @Enumerated(EnumType.STRING)
    private Team winner;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "room_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();;

    private int entranceCoin;

    private int totalCoin;

    ///// 도메인 메서드 /////

    public void changeStatus(GameStatus status) {
        this.status = status;
    }

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
        player.setTeam(determinePlayerTeam());
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        resetPlayerTeams();
    }

    private void recalculateTotalCoin(int entranceCoin) {
        this.totalCoin += entranceCoin;
    }

    private Team determinePlayerTeam() {
        int index = players.size();
        return switch (roomType) {
            case PVP -> index == 0 ? Team.RED : Team.BLUE;
            case PVPVP -> index == 0 ? Team.RED : (index == 1 ? Team.BLUE : Team.GREEN);
            case TVT -> index % 2 == 0 ? Team.RED : Team.BLUE;
        };
    }

    private void resetPlayerTeams() {
        for (Player player : players) {
            player.setTeam(determinePlayerTeam());
        }
    }
}