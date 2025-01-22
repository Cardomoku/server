package com.jjweidon.cardomoku.domain.game.entity;

import com.jjweidon.cardomoku.domain.game.entity.enums.BoardStatus;
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
public class Board extends BaseTime {

    @Id
    @Column(name = "board_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private int x;

    private int y;

    @Enumerated(EnumType.STRING)
    private Card card;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private BoardStatus state = BoardStatus.EMPTY;
}
