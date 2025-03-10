package com.jjweidon.cardomoku.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjweidon.cardomoku.domain.dto.GameMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final Map<String, Map<String, WebSocketSession>> gameRooms = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String gameId = extractGameId(session);
        gameRooms.computeIfAbsent(gameId, k -> new ConcurrentHashMap<>())
                .put(session.getId(), session);
        log.info("새로운 WebSocket 연결: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String gameId = extractGameId(session);
        GameMessage gameMessage = objectMapper.readValue(message.getPayload(), GameMessage.class);
        
        // 같은 게임룸의 다른 세션들에게 메시지 브로드캐스트
        Map<String, WebSocketSession> gameRoom = gameRooms.get(gameId);
        if (gameRoom != null) {
            gameRoom.values().forEach(s -> {
                try {
                    if (s.isOpen() && !s.getId().equals(session.getId())) {
                        s.sendMessage(message);
                    }
                } catch (Exception e) {
                    log.error("메시지 전송 실패", e);
                }
            });
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String gameId = extractGameId(session);
        Map<String, WebSocketSession> gameRoom = gameRooms.get(gameId);
        if (gameRoom != null) {
            gameRoom.remove(session.getId());
            if (gameRoom.isEmpty()) {
                gameRooms.remove(gameId);
            }
        }
        log.info("WebSocket 연결 종료: {}", session.getId());
    }

    private String extractGameId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
} 