package com.jjweidon.cardomoku.domain.entity;

import com.jjweidon.cardomoku.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseTime {
    @Id
    @Column(name = "player_card_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    private String suit;

    private String number;

    private String version;
}
