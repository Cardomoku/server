package com.jjweidon.cardomoku.domain.game.service;

import com.jjweidon.cardomoku.domain.game.dto.*;
import com.jjweidon.cardomoku.domain.game.entity.Board;
import com.jjweidon.cardomoku.domain.game.entity.Game;
import com.jjweidon.cardomoku.domain.game.entity.PlayerCard;
import com.jjweidon.cardomoku.domain.game.entity.enums.Card;
import com.jjweidon.cardomoku.domain.game.entity.enums.GameStatus;
import com.jjweidon.cardomoku.domain.game.exception.BoardNotFoundException;
import com.jjweidon.cardomoku.domain.game.exception.GameNotFoundException;
import com.jjweidon.cardomoku.domain.game.repository.GameRepository;
import com.jjweidon.cardomoku.domain.game.repository.PlayerCardRepository;
import com.jjweidon.cardomoku.domain.room.dto.PlayerData;
import com.jjweidon.cardomoku.domain.room.dto.StartGameRequest;
import com.jjweidon.cardomoku.domain.room.entity.Player;
import com.jjweidon.cardomoku.domain.room.entity.Room;
import com.jjweidon.cardomoku.domain.room.exception.PlayerNotFoundException;
import com.jjweidon.cardomoku.domain.room.exception.RoomNotFoundException;
import com.jjweidon.cardomoku.domain.room.repository.BoardRepository;
import com.jjweidon.cardomoku.domain.room.repository.PlayerRepository;
import com.jjweidon.cardomoku.domain.room.repository.RoomRepository;
import com.jjweidon.cardomoku.domain.user.entity.User;
import com.jjweidon.cardomoku.global.entity.enums.Color;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GameService {
    private final RoomRepository roomRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final BoardRepository boardRepository;
    private final PlayerCardRepository playerCardRepository;

    // 게임 시작
    public GameResponse startGame(User user, StartGameRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(RoomNotFoundException::new);
        Game game = Game.builder()
                .room(room)
                .build();
        room.changeStatus(GameStatus.IN_PROGRESS);
        roomRepository.save(room);
        gameRepository.save(game);
        initializeBoards(game);
        initializePlayerCards(game);
        return GameResponse.from(game);
    }

    // 게임판 조회
    public GameBoardResponse getGameBoard(String gameId) {
        Game game = findGameById(gameId);
        List<Board> boards = boardRepository.findByGame(game);
        List<BoardData> boardDataList = boards.stream()
                .map(BoardData::from)
                .toList();
        return GameBoardResponse.from(boardDataList);
    }

    // 플레이어 조회
    public PlayersResponse getPlayers(String gameId) {
        Game game = findGameById(gameId);
        List<Player> players = playerRepository.findByRoom(game.getRoom());
        List<PlayerData> playerDataList = players.stream()
                .map(PlayerData::from)
                .toList();
        return PlayersResponse.from(playerDataList);
    }

    // 나의 카드 조회
    public MyCardsResponse getMyCards(String gameId, User user) {
        Game game = findGameById(gameId);
        Player player = playerRepository.findByUser(user)
                .orElseThrow(PlayerNotFoundException::new);
        List<PlayerCard> playerCards = playerCardRepository.findByPlayer(player);
        List<MyCardData> myCardDataList = playerCards.stream()
                .map(MyCardData::from)
                .toList();
        return MyCardsResponse.from(myCardDataList);
    }

    // 카드 사용
    public void useCard(User user, UseCardRequest request) {
        Game game = findGameById(request.getGameId());
        Player player = playerRepository.findByUser(user)
                .orElseThrow(PlayerNotFoundException::new);
        Board board = findBoardById(request.getBoardId());
        if (board.getStatus() != Color.EMPTY) {
            throw new IllegalStateException("이미 채워진 Board");
        }
    }

    // 게임 상태 확인
    public GameStatusResponse checkGameOver(String gameId) {
        Game game = findGameById(gameId);
        return GameStatusResponse.from(game);
    }

    // 결과 조회
    public GameResultResponse getGameResult(String gameId) {
        Game game = findGameById(gameId);
        List<Player> players = playerRepository.findByRoom(game.getRoom());
        List<ContributionData> contributionDataList = players.stream()
                .map(ContributionData::from)
                .toList();
        return GameResultResponse.from(game, contributionDataList);
    }

    // Boards 초기화
    private void initializeBoards(Game game) {
        List<Integer> cardIds = IntStream.rangeClosed(1, 100)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(cardIds);

        List<Board> boards = new ArrayList<>();
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                int cardIndex = (x * 100 + y) % 100;
                Board board = Board.builder()
                        .game(game)
                        .x(x)
                        .y(y)
                        .card(Card.values()[cardIds.get(cardIndex) - 1])
                        .build();
                boards.add(board);
            }
        }
        boardRepository.saveAll(boards);
    }

    // PlayerCard 배분
    private void initializePlayerCards(Game game) {
        List<Card> allCards = new ArrayList<>(Arrays.asList(Card.values()));
        Collections.shuffle(allCards);

        List<Player> players = playerRepository.findByRoom(game.getRoom());

        final int CARDS_PER_PLAYER = 6;

        List<PlayerCard> playerCards = new ArrayList<>();
        for (Player player : players) {
            for (int i = 0; i < CARDS_PER_PLAYER; i++) {
                PlayerCard playerCard = PlayerCard.builder()
                        .player(player)
                        .card(allCards.remove(0))
                        .build();
                playerCards.add(playerCard);
            }
        }

        // 남은 카드는 게임 상태와 연관된 별도의 카드 더미로 관리 가능
        // 예: game.setRemainingCards(allCards);

        playerCardRepository.saveAll(playerCards);
    }

    // Game 찾기
    private Game findGameById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    // Player 찾기
    public Player findPlayerByUser(User user) {
        return playerRepository.findByUser(user)
                .orElseThrow(PlayerNotFoundException::new);
    }

    // Player 찾기
    private Board findBoardById(String boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }
}
