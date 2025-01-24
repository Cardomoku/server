package com.jjweidon.cardomoku.domain.game.entity;

import com.jjweidon.cardomoku.domain.game.entity.enums.Card;
import com.jjweidon.cardomoku.domain.room.entity.Player;
import com.jjweidon.cardomoku.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerCard extends BaseTime {

    @Id
    @Column(name = "player_card_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    private Card card;
}
