package com.jjweidon.cardomoku.domain.user.controller;

import com.jjweidon.cardomoku.domain.user.dto.*;
import com.jjweidon.cardomoku.global.dto.ApiResponse;
import com.jjweidon.cardomoku.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 프로필 조회
    @GetMapping("/profile")
    public ApiResponse<UserProfileResponse> getProfile(Authentication authentication) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("GET 회원 정보 조회: {}", userId);
        UserProfileResponse response = userService.getProfile(userId);
        return ApiResponse.success(response, "프로필 조회 성공");
    }

    // 프로필 수정
    @PutMapping("/profile")
    public ApiResponse<UpdateUserResponse> updateProfile(
            Authentication authentication,
            @RequestBody UpdateUserProfileRequest request) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        UpdateUserResponse response = userService.updateProfile(userId, request);
        return ApiResponse.success(response, "프로필 수정 성공");
    }

    // 코인 조회
    @GetMapping("/coins")
    public ApiResponse<UserCoinResponse> getCoins(Authentication authentication) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("GET 코인 조회: {}", userId);
        UserCoinResponse response = userService.getCoins(userId);
        return ApiResponse.success(response, "코인 조회 성공");
    }

    // 소리 설정 조회
    @GetMapping("/bgm")
    public ApiResponse<SoundSettingsResponse> getSoundSettings(Authentication authentication) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("GET 소리 설정 조회: {}", userId);
        SoundSettingsResponse response = userService.getSoundSettings(userId);
        return ApiResponse.success(response, "소리 설정 조회 성공");
    }

    // 소리 설정 수정
    @PutMapping("/bgm")
    public ApiResponse<UpdateUserResponse> updateSoundSettings(
            Authentication authentication,
            @RequestBody UpdateSoundSettingsRequest request) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("PUT 소리 설정 수정: {}", userId);
        UpdateUserResponse response = userService.updateSoundSettings(userId, request);
        return ApiResponse.success(response, "소리 설정 수정 성공");
    }
}