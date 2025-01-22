package com.jjweidon.cardomoku.domain.game.controller;

import com.jjweidon.cardomoku.domain.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

}
