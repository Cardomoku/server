package com.jjweidon.cardomoku.domain.user.entity;

import com.jjweidon.cardomoku.domain.user.entity.enums.Role;
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

    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int coin;

    private int win;

    private int lose;

    private int draw;

    @Setter
    @Builder.Default
    private boolean soundEffectsEnabled = true;

    @Setter
    @Builder.Default
    private boolean backgroundMusicEnabled = true;

    ///// 도메인 메서드 /////

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void changeProfileImage(String newImage) {
        this.image = newImage;
    }

    public void earnCoin(int amount) {
        this.coin += amount;
    }

    public void loseCoin(int amount) {
        if (this.coin < amount) {
            throw new IllegalStateException("코인이 부족합니다.");
        }
        this.coin -= amount;
    }

    public void addWin() {
        this.win++;
    }

    public void addLose() {
        this.lose++;
    }

    public void addDraw() {
        this.draw++;
    }
}