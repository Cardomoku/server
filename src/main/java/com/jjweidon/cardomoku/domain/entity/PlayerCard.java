package com.jjweidon.cardomoku.domain.entity;

import com.jjweidon.cardomoku.global.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerCard extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_card_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_card_id", nullable = false)
    private GameCard gameCard;
}
