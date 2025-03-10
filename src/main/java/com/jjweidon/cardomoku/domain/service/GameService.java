//package com.jjweidon.cardomoku.domain.service;
//
//import com.jjweidon.cardomoku.domain.dto.*;
//import com.jjweidon.cardomoku.domain.entity.*;
//import com.jjweidon.cardomoku.domain.entity.enums.GameStatus;
//import com.jjweidon.cardomoku.domain.entity.enums.Team;
//import com.jjweidon.cardomoku.domain.exception.BoardNotFoundException;
//import com.jjweidon.cardomoku.domain.exception.GameNotFoundException;
//import com.jjweidon.cardomoku.domain.exception.PlayerNotFoundException;
//import com.jjweidon.cardomoku.domain.exception.RoomNotFoundException;
//import com.jjweidon.cardomoku.domain.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Service
//@RequiredArgsConstructor
//public class GameService {
//    private final RoomRepository roomRepository;
//    private final PlayerRepository playerRepository;
//    private final BoardRepository boardRepository;
//    private final PlayerCardRepository playerCardRepository;
//    private final SimpMessagingTemplate messagingTemplate;
//
//    // 게임 시작
//    @Transactional
//    public GameResponse startGame(User user, StartGameRequest request) {
//        Room room = roomRepository.findById(request.getRoomId())
//                .orElseThrow(RoomNotFoundException::new);
//        Game game = Game.builder()
//                .room(room)
//                .build();
//        room.changeStatus(GameStatus.IN_PROGRESS);
//        roomRepository.save(room);
//        gameRepository.save(game);
//        initializeBoards(game);
//        initializePlayerCards(game);
//        return GameResponse.from(game);
//    }
//
//    // 게임판 조회
//    @Transactional(readOnly = true)
//    public GameBoardResponse getGameBoard(String gameId) {
//        Game game = findGameById(gameId);
//        List<GameBoard> gameBoards = boardRepository.findByGame(game);
//        List<BoardData> boardDataList = gameBoards.stream()
//                .map(BoardData::from)
//                .toList();
//        return GameBoardResponse.from(boardDataList);
//    }
//
//    // 플레이어 조회
//    @Transactional(readOnly = true)
//    public PlayersResponse getPlayers(String gameId) {
//        Game game = findGameById(gameId);
//        List<Player> players = playerRepository.findByRoom(game.getRoom());
//        List<PlayerData> playerDataList = players.stream()
//                .map(PlayerData::from)
//                .toList();
//        return PlayersResponse.from(playerDataList);
//    }
//
//    // 나의 카드 조회
//    @Transactional(readOnly = true)
//    public MyCardsResponse getMyCards(User user) {
//        Player player = playerRepository.findByUser(user)
//                .orElseThrow(PlayerNotFoundException::new);
//        List<PlayerCard> playerCards = playerCardRepository.findByPlayer(player);
//        List<MyCardData> myCardDataList = playerCards.stream()
//                .map(MyCardData::from)
//                .toList();
//        return MyCardsResponse.from(myCardDataList);
//    }
//
//    // 카드 사용
//    @Transactional
//    public void useCard(User user, UseCardRequest request) {
//        Game game = findGameById(request.getGameId());
//        Player player = findPlayerByUser(user);
//        GameBoard gameBoard = findBoardById(request.getBoardId());
//        PlayerCard playerCard = findPlayerCardById(request.getPlayerCardId());
//
//        validateTurn(player, game);
//        validateCardUsage(playerCard, gameBoard);
//
//        applyCardEffect(playerCard, gameBoard, player);
//        checkBingo(game, gameBoard, player);
//        checkWinCondition(game);
//        processNextTurn(game);
//        notifyGameStateChange(game, "CARD_USED", BoardData.from(gameBoard));
//    }
//
//    // 게임 상태 확인
//    @Transactional(readOnly = true)
//    public GameStatusResponse checkGameOver(String gameId) {
//        Game game = findGameById(gameId);
//        return GameStatusResponse.from(game);
//    }
//
//    // 결과 조회
//    @Transactional(readOnly = true)
//    public GameResultResponse getGameResult(String gameId) {
//        Game game = findGameById(gameId);
//        List<Player> players = playerRepository.findByRoom(game.getRoom());
//        List<ContributionData> contributionDataList = players.stream()
//                .map(ContributionData::from)
//                .toList();
//        return GameResultResponse.from(game, contributionDataList);
//    }
//
//    // Boards 초기화
//    private void initializeBoards(Game game) {
//        List<Integer> cardIds = IntStream.rangeClosed(1, 100)
//                .boxed()
//                .collect(Collectors.toList());
//        Collections.shuffle(cardIds);
//
//        List<GameBoard> gameBoards = new ArrayList<>();
//        for (int x = 0; x < 100; x++) {
//            for (int y = 0; y < 100; y++) {
//                int cardIndex = (x * 100 + y) % 100;
//                GameBoard gameBoard = GameBoard.builder()
//                        .game(game)
//                        .x(x)
//                        .y(y)
//                        .card(GameCard.values()[cardIds.get(cardIndex) - 1])
//                        .build();
//                gameBoards.add(gameBoard);
//            }
//        }
//        boardRepository.saveAll(gameBoards);
//    }
//
//    // PlayerCard 배분
//    private void initializePlayerCards(Game game) {
//        List<Player> players = playerRepository.findByRoom(game.getRoom());
//        final int CARDS_PER_PLAYER = 6;
//
//        List<PlayerCard> playerCards = new ArrayList<>();
//        for (Player player : players) {
//            for (int i = 0; i < CARDS_PER_PLAYER; i++) {
//                GameCard gameCard = game.drawCard();
//                PlayerCard playerCard = PlayerCard.builder()
//                        .player(player)
//                        .card(gameCard)
//                        .build();
//                playerCards.add(playerCard);
//            }
//        }
//
//        playerCardRepository.saveAll(playerCards);
//    }
//
//    // Game 찾기
//    private Game findGameById(String gameId) {
//        return gameRepository.findById(gameId)
//                .orElseThrow(() -> new GameNotFoundException(gameId));
//    }
//
//    // User로 Player 찾기
//    public Player findPlayerByUser(User user) {
//        return playerRepository.findByUser(user)
//                .orElseThrow(PlayerNotFoundException::new);
//    }
//
//    // Id로 Player 찾기
//    private GameBoard findBoardById(String boardId) {
//        return boardRepository.findById(boardId)
//                .orElseThrow(BoardNotFoundException::new);
//    }
//
//    private PlayerCard findPlayerCardById(String playerCardId) {
//        return playerCardRepository.findById(playerCardId)
//                .orElseThrow(PlayerNotFoundException::new);
//    }
//
//    private void validateTurn(Player player, Game game) {
//        if (!player.getIsTurn()) {
//            throw new IllegalStateException("현재 플레이어의 턴이 아닙니다.");
//        }
//    }
//
//    private void validateCardUsage(PlayerCard playerCard, GameBoard gameBoard) {
//        if (playerCard.getCard() != gameBoard.getCard()) {
//            throw new IllegalStateException("선택한 카드와 보드의 카드가 일치하지 않습니다.");
//        }
//        if (gameBoard.getStatus() != Team.DEFAULT) {
//            throw new IllegalStateException("이미 사용된 보드입니다.");
//        }
//    }
//
//    private void applyCardEffect(PlayerCard playerCard, GameBoard gameBoard, Player player) {
//        GameCard gameCard = playerCard.getCard();
//        if (isJCard(gameCard)) {
//            validateJCardUsage(gameCard, gameBoard);
//            applyJCardEffect(gameCard, gameBoard, player);
//        } else {
//            gameBoard.setStatus(player.getTeam());
//        }
//        playerCardRepository.delete(playerCard);
//        drawNewCard(player);
//    }
//
//    private void checkBingo(Game game, GameBoard gameBoard, Player player) {
//        List<GameBoard> gameBoards = boardRepository.findByGame(game);
//        Team playerTeam = player.getTeam();
//        int bingoCount = 0;
//        int fourCount = 0;
//
//        // 가로, 세로, 대각선 빙고 및 4개 체크
//        bingoCount += checkLine(gameBoards, gameBoard.getX(), gameBoard.getY(), playerTeam, Direction.HORIZONTAL, 5);
//        fourCount += checkLine(gameBoards, gameBoard.getX(), gameBoard.getY(), playerTeam, Direction.HORIZONTAL, 4);
//
//        bingoCount += checkLine(gameBoards, gameBoard.getX(), gameBoard.getY(), playerTeam, Direction.VERTICAL, 5);
//        fourCount += checkLine(gameBoards, gameBoard.getX(), gameBoard.getY(), playerTeam, Direction.VERTICAL, 4);
//
//        bingoCount += checkLine(gameBoards, gameBoard.getX(), gameBoard.getY(), playerTeam, Direction.DIAGONAL, 5);
//        fourCount += checkLine(gameBoards, gameBoard.getX(), gameBoard.getY(), playerTeam, Direction.DIAGONAL, 4);
//
//        if (bingoCount > 0) {
//            player.addBingoCount();
//        }
//
//        if (fourCount > 0) {
//            player.addFourCount();
//        }
//    }
//
//    private int checkLine(List<GameBoard> gameBoards, int x, int y, Team team, Direction direction, int targetCount) {
//        switch (direction) {
//            case HORIZONTAL:
//                return checkHorizontal(gameBoards, x, team, targetCount);
//            case VERTICAL:
//                return checkVertical(gameBoards, y, team, targetCount);
//            case DIAGONAL:
//                return checkDiagonal(gameBoards, x, y, team, targetCount);
//            default:
//                throw new IllegalArgumentException("Invalid direction: " + direction);
//        }
//    }
//
//    private int checkHorizontal(List<GameBoard> gameBoards, int x, Team team, int targetCount) {
//        int count = 0;
//        for (int i = 0; i < 10; i++) {
//            final int index = i;
//            boolean hasChip = gameBoards.stream()
//                    .anyMatch(b -> b.getX() == x && b.getY() == index && b.getStatus() == team);
//            if (hasChip) {
//                count++;
//                if (count >= targetCount) {
//                    return 1;
//                }
//            } else {
//                count = 0;
//            }
//        }
//        return 0;
//    }
//
//    private int checkVertical(List<GameBoard> gameBoards, int y, Team team, int targetCount) {
//        int count = 0;
//        for (int i = 0; i < 10; i++) {
//            final int index = i;
//            boolean hasChip = gameBoards.stream()
//                    .anyMatch(b -> b.getX() == index && b.getY() == y && b.getStatus() == team);
//            if (hasChip) {
//                count++;
//                if (count >= targetCount) {
//                    return 1;
//                }
//            } else {
//                count = 0;
//            }
//        }
//        return 0;
//    }
//
//    private int checkDiagonal(List<GameBoard> gameBoards, int x, int y, Team team, int targetCount) {
//        return checkDirection(gameBoards, x, y, team, targetCount, 1, 1) ||
//                checkDirection(gameBoards, x, y, team, targetCount, -1, 1) ? 1 : 0;
//    }
//
//    private boolean checkDirection(List<GameBoard> gameBoards, int x, int y, Team team, int targetCount, int dx, int dy) {
//        int count = 0;
//        for (int i = -4; i <= 4; i++) {
//            int checkX = x + i * dx;
//            int checkY = y + i * dy;
//            if (isWithinBounds(checkX, checkY) && hasChip(gameBoards, checkX, checkY, team)) {
//                count++;
//                if (count >= targetCount) return true;
//            } else {
//                count = 0;
//            }
//        }
//        return false;
//    }
//
//    private boolean isWithinBounds(int x, int y) {
//        return x >= 0 && x < 10 && y >= 0 && y < 10;
//    }
//
//    private boolean hasChip(List<GameBoard> gameBoards, int x, int y, Team team) {
//        return gameBoards.stream().anyMatch(b -> b.getX() == x && b.getY() == y && b.getStatus() == team);
//    }
//
//
//    private void checkWinCondition(Game game) {
//        List<Player> players = playerRepository.findByRoom(game.getRoom());
//
//        for (Player player : players) {
//            if (player.getBingoCount() >= 2) {
//                game.getRoom().changeStatus(GameStatus.TERMINATED);
//                distributeRewards(game, player.getTeam());
//                notifyGameStateChange(game, "GAME_OVER", GameResultResponse.from(game,
//                    players.stream().map(ContributionData::from).toList()));
//                break;
//            }
//        }
//    }
//
//    private void processNextTurn(Game game) {
//        List<Player> players = playerRepository.findByRoom(game.getRoom());
//        Player currentPlayer = players.stream()
//                .filter(Player::getIsTurn)
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("현재 턴인 플레이어를 찾을 수 없습니다."));
//        // 현재 플레이어 턴 종료
//        currentPlayer.endTurn();;
//        // 다음 플레이어 턴 시작
//        int currentIndex = players.indexOf(currentPlayer);
//        int nextIndex = (currentIndex + 1) % players.size();
//        Player nextPlayer = players.get(nextIndex);
//        nextPlayer.startTurn();
//        // 턴 변경 알림
//        notifyGameStateChange(game, "TURN_CHANGED", PlayerData.from(nextPlayer));
//    }
//
//    // 헬퍼 메서드들
//    private boolean isJCard(GameCard gameCard) {
//        return gameCard.getRank().equals("j");
//    }
//
//    private void validateJCardUsage(GameCard gameCard, GameBoard gameBoard) {
//        // J 카드 사용 검증 로직
//        if (gameCard.getVersion() == 1) {  // 한 눈 J
//            if (gameBoard.getStatus() == Team.DEFAULT) {
//                throw new IllegalStateException("한 눈 J 카드는 이미 놓여진 칩만 제거할 수 있습니다.");
//            }
//            if (isPartOfBingo(gameBoard)) {
//                throw new IllegalStateException("이 칩은 빙고를 구성합니다.");
//            }
//        } else {  // 두 눈 J
//            if (gameBoard.getStatus() != Team.DEFAULT) {
//                throw new IllegalStateException("두 눈 J 카드는 비어있는 칸에만 사용할 수 있습니다.");
//            }
//        }
//    }
//
//    private boolean isPartOfBingo(GameBoard gameBoard) {
//        List<GameBoard> gameBoards = boardRepository.findByGame(gameBoard.getGame());
//        Team team = gameBoard.getStatus();
//        // 가로, 세로, 대각선 빙고 체크
//        return checkLine(gameBoards, gameBoard.getX(), gameBoard.getY(), team, Direction.HORIZONTAL, 5) > 0 ||
//               checkLine(gameBoards, gameBoard.getX(), gameBoard.getY(), team, Direction.VERTICAL, 5) > 0 ||
//               checkLine(gameBoards, gameBoard.getX(), gameBoard.getY(), team, Direction.DIAGONAL, 5) > 0;
//    }
//
//    private void applyJCardEffect(GameCard gameCard, GameBoard gameBoard, Player player) {
//        if (gameCard.getVersion() == 1) {  // 한 눈 J
//            gameBoard.setStatus(Team.DEFAULT);
//        } else {  // 두 눈 J
//            gameBoard.setStatus(player.getTeam());
//        }
//        player.addJCount();
//    }
//
//    private void drawNewCard(Player player) {
//        Game game = gameRepository.findByRoom(player.getRoom())
//                .orElseThrow(GameNotFoundException::new);
//        GameCard newGameCard = game.drawCard();
//        PlayerCard playerCard = PlayerCard.builder()
//                .player(player)
//                .card(newGameCard)
//                .build();
//        playerCardRepository.save(playerCard);
//    }
//
//    private void distributeRewards(Game game, Team winningTeam) {
//        Room room = game.getRoom();
//        List<Player> winners = playerRepository.findByRoom(room).stream()
//                .filter(p -> p.getTeam() == winningTeam)
//                .toList();
//
//        int totalReward = room.getTotalCoin();
//        int totalContribution = winners.stream()
//                .mapToInt(p -> p.getBingoCount() * 3 + p.getJCount())
//                .sum();
//
//        for (Player winner : winners) {
//            int contribution = winner.getBingoCount() * 3 + winner.getJCount();
//            int reward = (int) ((double) contribution / totalContribution * totalReward);
//            winner.getUser().earnCoin(reward);
//            winner.getUser().addWin();
//        }
//
//        // 패배 팀 처리
//        playerRepository.findByRoom(room).stream()
//                .filter(p -> p.getTeam() != winningTeam)
//                .forEach(p -> p.getUser().addLose());
//    }
//
//    private void notifyGameStateChange(Game game, String type, Object data) {
//        GameMessage message = new GameMessage();
//        message.setType(type);
//        message.setGameId(game.getId());
//        message.setData(data);
//
//        messagingTemplate.convertAndSend("/topic/game/" + game.getId(), message);
//    }
//
//    private enum Direction {
//        HORIZONTAL, VERTICAL, DIAGONAL
//    }
//}
