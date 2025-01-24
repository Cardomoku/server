package com.jjweidon.cardomoku.domain.game.controller;

import com.jjweidon.cardomoku.domain.game.dto.*;
import com.jjweidon.cardomoku.domain.game.service.GameService;
import com.jjweidon.cardomoku.domain.room.dto.StartGameRequest;
import com.jjweidon.cardomoku.domain.user.entity.User;
import com.jjweidon.cardomoku.global.dto.ApiResponse;
import com.jjweidon.cardomoku.global.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    // 게임 시작
    @PostMapping("/start")
    public ApiResponse<Response> startGame(Authentication authentication, @RequestBody StartGameRequest request) {
        User user = (User) authentication.getPrincipal();
        GameResponse response = gameService.startGame(user, request);
        return ApiResponse.success(response, "게임 시작 성공");
    }

    // 게임판 조회
    @PostMapping("/board")
    public ApiResponse<Response> getGameBoard(Authentication authentication, @RequestBody FindGameRequest request) {
        GameBoardResponse response = gameService.getGameBoard(request.getGameId());
        return ApiResponse.success(response, "게임판 조회 성공");
    }

    // 플레이어 조회
    @PostMapping("/players")
    public ApiResponse<Response> getPlayers(Authentication authentication, @RequestBody FindGameRequest request) {
        PlayersResponse response = gameService.getPlayers(request.getGameId());
        return ApiResponse.success(response, "플레이어 조회 성공");
    }

    // 나의 카드 조회
    @PostMapping("/my-cards")
    public ApiResponse<Response> getMyCards(Authentication authentication, @RequestBody FindGameRequest request) {
        User user = (User) authentication.getPrincipal();
        MyCardsResponse response = gameService.getMyCards(request.getGameId(), user);
        return ApiResponse.success(response, "나의 카드 조회 성공");
    }

    // 카드 사용
    @PostMapping("/cards/use")
    public ApiResponse<Response> useCard(Authentication authentication, @RequestBody UseCardRequest request) {
        User user = (User) authentication.getPrincipal();
        gameService.useCard(user, request);
        return ApiResponse.success(null, "카드 사용 성공");
    }

    // 게임 종료 상태 확인
    @PostMapping("/status")
    public ApiResponse<Response> checkGameOver(Authentication authentication, @RequestBody FindGameRequest request) {
        GameStatusResponse response = gameService.checkGameOver(request.getGameId());
        return ApiResponse.success(response, "게임 종료 상태 확인 성공");
    }

    // 결과 조회
    @PostMapping("/result")
    public ApiResponse<Response> getGameResult(Authentication authentication, @RequestBody FindGameRequest request) {
        GameResultResponse response = gameService.getGameResult(request.getGameId());
        return ApiResponse.success(response, "결과 조회 성공");
    }
}