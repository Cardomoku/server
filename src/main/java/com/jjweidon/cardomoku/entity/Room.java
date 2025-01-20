package com.jjweidon.cardomoku.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String code;

    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<User> players;
}
