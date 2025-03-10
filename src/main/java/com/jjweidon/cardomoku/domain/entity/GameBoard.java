package com.jjweidon.cardomoku.domain.entity;

import com.jjweidon.cardomoku.global.entity.BaseTime;
import com.jjweidon.cardomoku.domain.entity.enums.Team;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameBoard extends BaseTime {

    @Id
    @Column(name = "board_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    private int x;

    private int y;

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Team status = Team.DEFAULT;
}
