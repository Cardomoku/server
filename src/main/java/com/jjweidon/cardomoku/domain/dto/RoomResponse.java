package com.jjweidon.cardomoku.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.entity.Room;
import com.jjweidon.cardomoku.domain.entity.enums.RoomType;
import com.jjweidon.cardomoku.global.dto.Response;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomResponse implements Response {
    private String roomId;
    private RoomType roomType;
    private LocalDateTime createdAt;

    public static RoomResponse from(Room room) {
        return RoomResponse.builder()
                .roomId(room.getId())
                .roomType(room.getRoomType())
                .createdAt(room.getCreatedAt())
                .build();
    }
}