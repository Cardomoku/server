package com.jjweidon.cardomoku.domain.room.entity;

import com.jjweidon.cardomoku.domain.user.entity.User;
import com.jjweidon.cardomoku.global.entity.enums.Color;
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

    @Builder.Default
    private Boolean isOwner = false;

    @Setter
    @Enumerated(EnumType.STRING)
    private Color color;

    @Builder.Default
    private Boolean isTurn = false;

    int fourCreated;

    int bingoCreated;

    int jUsed;

    ///// 도메인 메서드 /////

    public void startTurn() {
        this.isTurn = true;
    }

    public void endTurn() {
        this.isTurn = false;
    }
}
