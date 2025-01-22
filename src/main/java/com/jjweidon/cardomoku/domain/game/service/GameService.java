package com.jjweidon.cardomoku.domain.game.service;

import com.jjweidon.cardomoku.domain.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

}
