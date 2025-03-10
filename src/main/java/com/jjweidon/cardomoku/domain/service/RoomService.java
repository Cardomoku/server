//package com.jjweidon.cardomoku.domain.service;
//
//import com.jjweidon.cardomoku.domain.dto.*;
//import com.jjweidon.cardomoku.domain.entity.Game;
//import com.jjweidon.cardomoku.domain.entity.enums.GameStatus;
//import com.jjweidon.cardomoku.domain.entity.Player;
//import com.jjweidon.cardomoku.domain.entity.Room;
//import com.jjweidon.cardomoku.domain.entity.enums.RoomType;
//import com.jjweidon.cardomoku.domain.exception.RoomNotFoundException;
//import com.jjweidon.cardomoku.domain.repository.GameRepository;
//import com.jjweidon.cardomoku.domain.repository.PlayerRepository;
//import com.jjweidon.cardomoku.domain.repository.RoomRepository;
//import com.jjweidon.cardomoku.domain.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class RoomService {
//
//    private final RoomRepository roomRepository;
//    private final PlayerRepository playerRepository;
//
//    // 방 생성
//    public RoomResponse createRoom(User user, CreateRoomRequest request) {
//        Room room = Room.builder()
//                .roomType(request.getRoomType())
//                .code(generateUniqueRoomCode())
//                .entranceCoin(request.getEntranceCoin())
//                .build();
//        roomRepository.save(room);
//        createPlayerForRoom(user, room, true);
//        return RoomResponse.from(room);
//    }
//
//    // 코드로 방 입장
//    public RoomResponse joinRoomWithCode(User user, RoomCodeRequest request) {
//        Room room = roomRepository.findByCode(request.getRoomCode())
//                .orElseThrow(() -> new RoomNotFoundException(request.getRoomCode()));
//        createPlayerForRoom(user, room, false);
//        return RoomResponse.from(room);
//    }
//
//    // 빠른 입장
//    public RoomResponse quickJoinRoom(User user, RoomTypeRequest request) {
//        RoomType roomType = request.getRoomType();
//        Room room = roomRepository.findFirstByRoomTypeAndStatus(roomType, GameStatus.NOT_STARTED)
//                .orElseThrow(RoomNotFoundException::new);
//        createPlayerForRoom(user, room, false);
//        return RoomResponse.from(room);
//    }
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
//    // 방 퇴장
//    public void leaveRoom(User user) {
//        Player player = gameService.findPlayerByUser(user);
//        Room room = player.getRoom();
//        room.removePlayer(player);
//        playerRepository.delete(player);
//    }
//
//    // 방 코드 생성
//    private String generateUniqueRoomCode() {
//        String roomCode;
//        do {
//            roomCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
//        } while (roomRepository.existsByCode(roomCode));
//        return roomCode;
//    }
//
//    // 플레이어 생성
//    private void createPlayerForRoom(User user, Room room, boolean isOwner) {
//        Player player = Player.builder()
//                .user(user)
//                .room(room)
//                .isOwner(isOwner)
//                .isTurn(isOwner)
//                .build();
//        room.addPlayer(player);
//        playerRepository.save(player);
//    }
//}