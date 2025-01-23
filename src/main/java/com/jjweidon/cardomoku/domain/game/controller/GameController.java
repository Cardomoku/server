package com.jjweidon.cardomoku.domain.game.controller;

import com.jjweidon.cardomoku.domain.game.dto.*;
import com.jjweidon.cardomoku.domain.game.service.GameService;
import com.jjweidon.cardomoku.global.dto.ApiResponse;
import com.jjweidon.cardomoku.global.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

//    @PostMapping("/start")
//    public ApiResponse<Response> startGame(Authentication authentication,  @RequestBody StartGameRequest request) {
//        GameResponse response = gameService.startGame(request);
//        return ApiResponse.success(response, "게임 시작 성공");
//    }
//
//    @GetMapping("/board")
//    public ApiResponse<Response> getGameBoard(Authentication authentication, @RequestParam String gameId) {
//        GameBoardResponse response = gameService.getGameBoard(gameId);
//        return ApiResponse.success(response, "게임판 조회 성공");
//    }
//
//    @GetMapping("/players")
//    public ApiResponse<Response> getPlayers(Authentication authentication, @RequestParam String gameId) {
//        PlayersResponse response = gameService.getPlayers(gameId);
//        return ApiResponse.success(response, "플레이어 조회 성공");
//    }
//
//    @GetMapping("/my-cards")
//    public ApiResponse<Response> getMyCards(Authentication authentication, @RequestParam String gameId, @RequestParam String playerId) {
//        MyCardsResponse response = gameService.getMyCards(gameId, playerId);
//        return ApiResponse.success(response, "나의 카드 조회 성공");
//    }
//
//    @PostMapping("/cards/use")
//    public ApiResponse<Response> useCard(Authentication authentication, @RequestBody UseCardRequest request) {
//        UseCardResponse response = gameService.useCard(request);
//        return ApiResponse.success(response, "카드 사용 성공");
//    }
//
//    @PostMapping("/status")
//    public ApiResponse<Response> checkGameOver(Authentication authentication, @RequestParam String gameId) {
//        GameStatusResponse response = gameService.checkGameOver(gameId);
//        return ApiResponse.success(response, "게임 종료 상태 확인 성공");
//    }
//
//    @GetMapping("/result")
//    public ApiResponse<Response> getGameResult(Authentication authentication, @RequestParam String gameId) {
//        GameResultResponse response = gameService.getGameResult(gameId);
//        return ApiResponse.success(response, "결과 조회 성공");
//    }
}