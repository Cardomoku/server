package com.jjweidon.cardomoku.domain.user.service;

import com.jjweidon.cardomoku.domain.user.dto.UpdateUserProfileRequest;
import com.jjweidon.cardomoku.domain.user.dto.UserProfileResponse;
import com.jjweidon.cardomoku.domain.user.entity.User;
import com.jjweidon.cardomoku.domain.user.exception.UserNotFoundException;
import com.jjweidon.cardomoku.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 프로필 조회
    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(String userId) {
        User user = findUserByUserId(userId);
        return UserProfileResponse.from(user);
    }

    // 프로필 수정
    @Transactional
    public UserProfileResponse updateProfile(String userId, UpdateUserProfileRequest request) {
        User user = findUserByUserId(userId);
        user.changeNickname(request.getNickname());
        user.changeProfileImage(request.getImage());
        userRepository.save(user);
        return UserProfileResponse.from(user);
    }

    public User findUserByUserId(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
