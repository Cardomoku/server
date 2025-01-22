package com.jjweidon.cardomoku.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jjweidon.cardomoku.entity.enums.GameStatus;
import com.jjweidon.cardomoku.entity.enums.RoomType;
import com.jjweidon.cardomoku.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

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
    @OneToMany(mappedBy = "room_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Player> players;

    private int totalCoins;
}