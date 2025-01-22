package com.jjweidon.cardomoku.entity;

import com.jjweidon.cardomoku.entity.enums.Color;
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
    private Boolean is_owner = false;

    @Enumerated(EnumType.STRING)
    private Color color;

    int fourCreated;

    int bingoCreated;

    int jsUsed;
}
