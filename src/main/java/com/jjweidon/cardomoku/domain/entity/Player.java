package com.jjweidon.cardomoku.domain.entity;

import com.jjweidon.cardomoku.domain.entity.enums.PlayerStatus;
import com.jjweidon.cardomoku.domain.entity.enums.Team;
import com.jjweidon.cardomoku.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player extends BaseTime {

    @Id
    @Column(name = "player_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Setter
    @Enumerated(EnumType.STRING)
    private Team team;

    private boolean isOwner;

    private boolean isTurn;

    private int bingoCount;

    private int jCount;

    private int fourCount;

    private int threeCount;

    private PlayerStatus status;

    ///// 도메인 메서드 /////

    public void startTurn() {
        this.isTurn = true;
    }

    public void endTurn() {
        this.isTurn = false;
    }

    public void addBingoCount() {
        this.bingoCount++;
    }

    public void addJCount() {
        this.jCount++;
    }

    public void addFourCount() {
        this.fourCount++;
    }

    public void addThreeCount() {
        this.threeCount++;
    }
}
