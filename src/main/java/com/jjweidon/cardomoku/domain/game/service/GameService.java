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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final SimpMessagingTemplate messagingTemplate;

    // 게임 시작
    @Transactional
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
    @Transactional(readOnly = true)
    public GameBoardResponse getGameBoard(String gameId) {
        Game game = findGameById(gameId);
        List<Board> boards = boardRepository.findByGame(game);
        List<BoardData> boardDataList = boards.stream()
                .map(BoardData::from)
                .toList();
        return GameBoardResponse.from(boardDataList);
    }

    // 플레이어 조회
    @Transactional(readOnly = true)
    public PlayersResponse getPlayers(String gameId) {
        Game game = findGameById(gameId);
        List<Player> players = playerRepository.findByRoom(game.getRoom());
        List<PlayerData> playerDataList = players.stream()
                .map(PlayerData::from)
                .toList();
        return PlayersResponse.from(playerDataList);
    }

    // 나의 카드 조회
    @Transactional(readOnly = true)
    public MyCardsResponse getMyCards(User user) {
        Player player = playerRepository.findByUser(user)
                .orElseThrow(PlayerNotFoundException::new);
        List<PlayerCard> playerCards = playerCardRepository.findByPlayer(player);
        List<MyCardData> myCardDataList = playerCards.stream()
                .map(MyCardData::from)
                .toList();
        return MyCardsResponse.from(myCardDataList);
    }

    // 카드 사용
    @Transactional
    public void useCard(User user, UseCardRequest request) {
        Game game = findGameById(request.getGameId());
        Player player = findPlayerByUser(user);
        Board board = findBoardById(request.getBoardId());
        PlayerCard playerCard = findPlayerCardById(request.getPlayerCardId());
        
        validateTurn(player, game);
        validateCardUsage(playerCard, board);
        
        applyCardEffect(playerCard, board, player);
        checkBingo(game, board, player);
        checkWinCondition(game);
        processNextTurn(game);
        notifyGameStateChange(game, "CARD_USED", BoardData.from(board));
    }

    // 게임 상태 확인
    @Transactional(readOnly = true)
    public GameStatusResponse checkGameOver(String gameId) {
        Game game = findGameById(gameId);
        return GameStatusResponse.from(game);
    }

    // 결과 조회
    @Transactional(readOnly = true)
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
        List<Player> players = playerRepository.findByRoom(game.getRoom());
        final int CARDS_PER_PLAYER = 6;

        List<PlayerCard> playerCards = new ArrayList<>();
        for (Player player : players) {
            for (int i = 0; i < CARDS_PER_PLAYER; i++) {
                Card card = game.drawCard();
                PlayerCard playerCard = PlayerCard.builder()
                        .player(player)
                        .card(card)
                        .build();
                playerCards.add(playerCard);
            }
        }

        playerCardRepository.saveAll(playerCards);
    }

    // Game 찾기
    private Game findGameById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    // User로 Player 찾기
    public Player findPlayerByUser(User user) {
        return playerRepository.findByUser(user)
                .orElseThrow(PlayerNotFoundException::new);
    }

    // Id로 Player 찾기
    private Board findBoardById(String boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }

    private PlayerCard findPlayerCardById(String playerCardId) {
        return playerCardRepository.findById(playerCardId)
                .orElseThrow(PlayerNotFoundException::new);
    }

    private void validateTurn(Player player, Game game) {
        if (!player.getIsTurn()) {
            throw new IllegalStateException("현재 플레이어의 턴이 아닙니다.");
        }
    }

    private void validateCardUsage(PlayerCard playerCard, Board board) {
        if (playerCard.getCard() != board.getCard()) {
            throw new IllegalStateException("선택한 카드와 보드의 카드가 일치하지 않습니다.");
        }
        if (board.getStatus() != Color.EMPTY) {
            throw new IllegalStateException("이미 사용된 보드입니다.");
        }
    }

    private void applyCardEffect(PlayerCard playerCard, Board board, Player player) {
        Card card = playerCard.getCard();
        if (isJCard(card)) {
            validateJCardUsage(card, board);
            applyJCardEffect(card, board, player);
        } else {
            board.setStatus(player.getColor());
        }
        playerCardRepository.delete(playerCard);
        drawNewCard(player);
    }

    private void checkBingo(Game game, Board board, Player player) {
        List<Board> boards = boardRepository.findByGame(game);
        Color playerColor = player.getColor();
        int bingoCount = 0;
        int fourCount = 0;

        // 가로, 세로, 대각선 빙고 및 4개 체크
        bingoCount += checkLine(boards, board.getX(), board.getY(), playerColor, Direction.HORIZONTAL, 5);
        fourCount += checkLine(boards, board.getX(), board.getY(), playerColor, Direction.HORIZONTAL, 4);

        bingoCount += checkLine(boards, board.getX(), board.getY(), playerColor, Direction.VERTICAL, 5);
        fourCount += checkLine(boards, board.getX(), board.getY(), playerColor, Direction.VERTICAL, 4);

        bingoCount += checkLine(boards, board.getX(), board.getY(), playerColor, Direction.DIAGONAL, 5);
        fourCount += checkLine(boards, board.getX(), board.getY(), playerColor, Direction.DIAGONAL, 4);

        if (bingoCount > 0) {
            player.addBingoCreated();
        }

        if (fourCount > 0) {
            player.addFourCreated();
        }
    }

    private int checkLine(List<Board> boards, int x, int y, Color color, Direction direction, int targetCount) {
        switch (direction) {
            case HORIZONTAL:
                return checkHorizontal(boards, x, color, targetCount);
            case VERTICAL:
                return checkVertical(boards, y, color, targetCount);
            case DIAGONAL:
                return checkDiagonal(boards, x, y, color, targetCount);
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    private int checkHorizontal(List<Board> boards, int x, Color color, int targetCount) {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            final int index = i;
            boolean hasChip = boards.stream()
                    .anyMatch(b -> b.getX() == x && b.getY() == index && b.getStatus() == color);
            if (hasChip) {
                count++;
                if (count >= targetCount) {
                    return 1;
                }
            } else {
                count = 0;
            }
        }
        return 0;
    }

    private int checkVertical(List<Board> boards, int y, Color color, int targetCount) {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            final int index = i;
            boolean hasChip = boards.stream()
                    .anyMatch(b -> b.getX() == index && b.getY() == y && b.getStatus() == color);
            if (hasChip) {
                count++;
                if (count >= targetCount) {
                    return 1;
                }
            } else {
                count = 0;
            }
        }
        return 0;
    }

    private int checkDiagonal(List<Board> boards, int x, int y, Color color, int targetCount) {
        int count = 0;

        // 대각선 (\) 체크
        for (int i = -4; i <= 4; i++) {
            int checkX = x + i;
            int checkY = y + i;
            if (checkX < 0 || checkX >= 10 || checkY < 0 || checkY >= 10) {
                continue;
            }
            boolean hasChip = boards.stream()
                    .anyMatch(b -> b.getX() == checkX && b.getY() == checkY && b.getStatus() == color);
            if (hasChip) {
                count++;
                if (count >= targetCount) {
                    return 1;
                }
            } else {
                count = 0;
            }
        }

        // 대각선 (/) 체크
        count = 0;
        for (int i = -4; i <= 4; i++) {
            int checkX = x - i;
            int checkY = y + i;
            if (checkX < 0 || checkX >= 10 || checkY < 0 || checkY >= 10) {
                continue;
            }
            boolean hasChip = boards.stream()
                    .anyMatch(b -> b.getX() == checkX && b.getY() == checkY && b.getStatus() == color);
            if (hasChip) {
                count++;
                if (count >= targetCount) {
                    return 1;
                }
            } else {
                count = 0;
            }
        }

        return 0;
    }

    private void checkWinCondition(Game game) {
        List<Player> players = playerRepository.findByRoom(game.getRoom());
        
        for (Player player : players) {
            if (player.getBingoCreated() >= 2) {
                // 게임 종료 처리
                game.getRoom().changeStatus(GameStatus.TERMINATED);
                distributeRewards(game, player.getColor());
                notifyGameStateChange(game, "GAME_OVER", GameResultResponse.from(game, 
                    players.stream().map(ContributionData::from).toList()));
                break;
            }
        }
    }

    private void processNextTurn(Game game) {
        List<Player> players = playerRepository.findByRoom(game.getRoom());
        Player currentPlayer = players.stream()
                .filter(Player::getIsTurn)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("현재 턴인 플레이어를 찾을 수 없습니다."));
        // 현재 플레이어 턴 종료
        currentPlayer.endTurn();;
        // 다음 플레이어 턴 시작
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        Player nextPlayer = players.get(nextIndex);
        nextPlayer.startTurn();
        // 턴 변경 알림
        notifyGameStateChange(game, "TURN_CHANGED", PlayerData.from(nextPlayer));
    }

    // 헬퍼 메서드들
    private boolean isJCard(Card card) {
        return card.getRank().equals("j");
    }

    private void validateJCardUsage(Card card, Board board) {
        // J 카드 사용 검증 로직
        if (card.getVersion() == 1) {  // 한 눈 J
            if (board.getStatus() == Color.EMPTY) {
                throw new IllegalStateException("한 눈 J 카드는 이미 놓여진 칩만 제거할 수 있습니다.");
            }
            // 빙고를 구성하는 칩인지 확인하는 로직 추가
            if (isPartOfBingo(board)) {
                throw new IllegalStateException("이 칩은 빙고를 구성합니다.");
            }
        } else {  // 두 눈 J
            if (board.getStatus() != Color.EMPTY) {
                throw new IllegalStateException("두 눈 J 카드는 비어있는 칸에만 사용할 수 있습니다.");
            }
        }
    }

    private boolean isPartOfBingo(Board board) {
        List<Board> boards = boardRepository.findByGame(board.getGame());
        Color color = board.getStatus();
        // 가로, 세로, 대각선 빙고 체크
        return checkLine(boards, board.getX(), board.getY(), color, Direction.HORIZONTAL, 5) > 0 ||
               checkLine(boards, board.getX(), board.getY(), color, Direction.VERTICAL, 5) > 0 ||
               checkLine(boards, board.getX(), board.getY(), color, Direction.DIAGONAL, 5) > 0;
    }

    private void applyJCardEffect(Card card, Board board, Player player) {
        if (card.getVersion() == 1) {  // 한 눈 J
            board.setStatus(Color.EMPTY);
        } else {  // 두 눈 J
            board.setStatus(player.getColor());
        }
        player.addJUsed();
    }

    private void drawNewCard(Player player) {
        Game game = gameRepository.findByRoom(player.getRoom())
                .orElseThrow(GameNotFoundException::new);
        Card newCard = game.drawCard();
        PlayerCard playerCard = PlayerCard.builder()
                .player(player)
                .card(newCard)
                .build();
        playerCardRepository.save(playerCard);
    }

    private void distributeRewards(Game game, Color winningColor) {
        Room room = game.getRoom();
        List<Player> winners = playerRepository.findByRoom(room).stream()
                .filter(p -> p.getColor() == winningColor)
                .toList();
        
        int totalReward = room.getTotalCoin();
        int totalContribution = winners.stream()
                .mapToInt(p -> p.getBingoCreated() * 3 + p.getJUsed())
                .sum();
        
        for (Player winner : winners) {
            int contribution = winner.getBingoCreated() * 3 + winner.getJUsed();
            int reward = (int) ((double) contribution / totalContribution * totalReward);
            winner.getUser().earnCoin(reward);
            winner.getUser().addWin();
        }
        
        // 패배 팀 처리
        playerRepository.findByRoom(room).stream()
                .filter(p -> p.getColor() != winningColor)
                .forEach(p -> p.getUser().addLose());
    }

    private void notifyGameStateChange(Game game, String type, Object data) {
        GameMessage message = new GameMessage();
        message.setType(type);
        message.setGameId(game.getId());
        message.setData(data);
        
        messagingTemplate.convertAndSend("/topic/game/" + game.getId(), message);
    }

    private enum Direction {
        HORIZONTAL, VERTICAL, DIAGONAL
    }
}
