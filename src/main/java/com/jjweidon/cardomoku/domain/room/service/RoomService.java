package com.jjweidon.cardomoku.domain.room.service;

import com.jjweidon.cardomoku.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

}
