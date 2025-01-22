package com.jjweidon.cardomoku.domain.room.controller;

import com.jjweidon.cardomoku.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


}
