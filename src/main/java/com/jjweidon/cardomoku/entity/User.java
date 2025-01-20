package com.jjweidon.cardomoku.entity;

import com.jjweidon.cardomoku.entity.enums.Role;
import com.jjweidon.cardomoku.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id
    @Column(name = "user_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int coin;

    private int win;

    private int lose;

    private int draw;

    @Builder.Default
    private boolean soundEffectsEnabled = true;

    @Builder.Default
    private boolean backgroundMusicEnabled = true;
}
