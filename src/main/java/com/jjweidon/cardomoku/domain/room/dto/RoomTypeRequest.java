package com.jjweidon.cardomoku.domain.room.dto;

import com.jjweidon.cardomoku.domain.room.entity.enums.RoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomTypeRequest {
    private RoomType roomType;
}