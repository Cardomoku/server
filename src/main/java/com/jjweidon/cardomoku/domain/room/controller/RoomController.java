package com.jjweidon.cardomoku.domain.room.controller;

import com.jjweidon.cardomoku.domain.game.dto.GameResponse;
import com.jjweidon.cardomoku.domain.room.dto.*;
import com.jjweidon.cardomoku.domain.room.service.RoomService;
import com.jjweidon.cardomoku.domain.user.entity.User;
import com.jjweidon.cardomoku.global.dto.ApiResponse;
import com.jjweidon.cardomoku.global.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    // 방 생성
    @PostMapping("/create")
    public ApiResponse<Response> createRoom(Authentication authentication, @RequestBody CreateRoomRequest request) {
        User user = (User) authentication.getPrincipal();
        RoomResponse response = roomService.createRoom(user, request);
        return ApiResponse.created(response, "방 생성 성공");
    }

    // 코드로 입장
    @PostMapping("/join/code")
    public ApiResponse<Response> joinRoomWithCode(Authentication authentication, @RequestBody RoomCodeRequest request) {
        User user = (User) authentication.getPrincipal();
        RoomResponse response = roomService.joinRoomWithCode(user, request);
        return ApiResponse.success(response, "코드로 방 입장 성공");
    }

    // 빠른 입장
    @PostMapping("/join/quick")
    public ApiResponse<Response> quickJoinRoom(Authentication authentication, @RequestBody RoomTypeRequest request) {
        User user = (User) authentication.getPrincipal();
        RoomResponse response = roomService.quickJoinRoom(user, request);
        return ApiResponse.success(response, "빠른 입장 성공");
    }

    // 방 퇴장
    @DeleteMapping("/leave")
    public ApiResponse<Response> leaveRoom(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        roomService.leaveRoom(user);
        return ApiResponse.noContent("방 퇴장 성공");
    }
}