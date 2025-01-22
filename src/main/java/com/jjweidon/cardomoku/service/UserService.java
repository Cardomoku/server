package com.jjweidon.cardomoku.service;

import com.jjweidon.cardomoku.dto.UpdateUserProfileRequest;
import com.jjweidon.cardomoku.dto.UserProfileResponse;
import com.jjweidon.cardomoku.entity.User;
import com.jjweidon.cardomoku.exception.UserNotFoundException;
import com.jjweidon.cardomoku.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 프로필 조회
    public UserProfileResponse getProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return UserProfileResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .coin(user.getCoin())
                .win(user.getWin())
                .lose(user.getLose())
                .draw(user.getDraw())
                .build();
    }

    // 프로필 수정
    public void updateProfile(String userId, UpdateUserProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.changeNickname(request.getNickname());
        user.changeProfileImage(request.getImage());
        userRepository.save(user);
    }
}
