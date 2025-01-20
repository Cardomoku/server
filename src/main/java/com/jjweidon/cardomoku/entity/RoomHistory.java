package com.jjweidon.cardomoku.entity;

import com.jjweidon.cardomoku.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomHistory extends BaseTime {

    @Id
    @Column(name = "room_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long turn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private User player;

    private int card;

    private int position;
}
