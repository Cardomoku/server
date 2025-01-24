package com.jjweidon.cardomoku.domain.room.service;

import com.jjweidon.cardomoku.domain.game.entity.enums.GameStatus;
import com.jjweidon.cardomoku.domain.game.service.GameService;
import com.jjweidon.cardomoku.domain.room.dto.CreateRoomRequest;
import com.jjweidon.cardomoku.domain.room.dto.RoomCodeRequest;
import com.jjweidon.cardomoku.domain.room.dto.RoomResponse;
import com.jjweidon.cardomoku.domain.room.dto.RoomTypeRequest;
import com.jjweidon.cardomoku.domain.room.entity.Player;
import com.jjweidon.cardomoku.domain.room.entity.Room;
import com.jjweidon.cardomoku.domain.room.entity.enums.RoomType;
import com.jjweidon.cardomoku.domain.room.exception.RoomNotFoundException;
import com.jjweidon.cardomoku.domain.room.repository.PlayerRepository;
import com.jjweidon.cardomoku.domain.room.repository.RoomRepository;
import com.jjweidon.cardomoku.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final PlayerRepository playerRepository;
    private final GameService gameService;

    // 방 생성
    public RoomResponse createRoom(User user, CreateRoomRequest request) {
        Room room = Room.builder()
                .roomType(request.getRoomType())
                .code(generateUniqueRoomCode())
                .entranceCoin(request.getEntranceCoin())
                .build();
        roomRepository.save(room);
        createPlayerForRoom(user, room, true);
        return RoomResponse.from(room);
    }

    // 코드로 방 입장
    public RoomResponse joinRoomWithCode(User user, RoomCodeRequest request) {
        Room room = roomRepository.findByCode(request.getRoomCode())
                .orElseThrow(() -> new RoomNotFoundException(request.getRoomCode()));
        createPlayerForRoom(user, room, false);
        return RoomResponse.from(room);
    }

    // 빠른 입장
    public RoomResponse quickJoinRoom(User user, RoomTypeRequest request) {
        RoomType roomType = request.getRoomType();
        Room room = roomRepository.findFirstByRoomTypeAndStatus(roomType, GameStatus.NOT_STARTED)
                .orElseThrow(RoomNotFoundException::new);
        createPlayerForRoom(user, room, false);
        return RoomResponse.from(room);
    }

    // 방 퇴장
    public void leaveRoom(User user) {
        Player player = gameService.findPlayerByUser(user);
        Room room = player.getRoom();
        room.removePlayer(player);
        playerRepository.delete(player);
    }

    // 방 코드 생성
    private String generateUniqueRoomCode() {
        String roomCode;
        do {
            roomCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        } while (roomRepository.existsByCode(roomCode));
        return roomCode;
    }

    // 플레이어 생성
    private void createPlayerForRoom(User user, Room room, boolean isOwner) {
        Player player = Player.builder()
                .user(user)
                .room(room)
                .isOwner(isOwner)
                .isTurn(isOwner)
                .build();
        room.addPlayer(player);
        playerRepository.save(player);
    }
}