package com.jjweidon.cardomoku.domain.game.entity;

import com.jjweidon.cardomoku.domain.room.entity.Player;
import com.jjweidon.cardomoku.domain.game.entity.enums.ActionType;
import com.jjweidon.cardomoku.global.entity.enums.Card;
import com.jjweidon.cardomoku.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameHistory extends BaseTime {

    @Id
    @Column(name = "game_history_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    private Card usedCard;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;
}
