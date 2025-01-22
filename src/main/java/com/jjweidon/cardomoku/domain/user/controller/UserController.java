package com.jjweidon.cardomoku.domain.user.controller;

import com.jjweidon.cardomoku.domain.user.dto.*;
import com.jjweidon.cardomoku.global.dto.ApiResponse;
import com.jjweidon.cardomoku.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 프로필 조회
    @GetMapping("/users/me/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("GET 회원 정보 조회: {}", userId);
        UserProfileResponse response = userService.getProfile(userId);
        return ResponseEntity.ok(response);
    }

    // 프로필 수정
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateProfile(
            Authentication authentication,
            @RequestBody UpdateUserProfileRequest request) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        UpdateUserResponse response = userService.updateProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success(response, "프로필이 성공적으로 수정되었습니다."));
    }

    // 코인 조회
    @GetMapping("/coins")
    public ResponseEntity<ApiResponse<UserCoinResponse>> getCoins(Authentication authentication) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("GET 코인 조회: {}", userId);
        UserCoinResponse response = userService.getCoins(userId);
        return ResponseEntity.ok(ApiResponse.success(response, "코인 조회 성공"));
    }

    // 소리 설정 조회
    @GetMapping("/bgm")
    public ResponseEntity<ApiResponse<SoundSettingsResponse>> getSoundSettings(Authentication authentication) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("GET 소리 설정 조회: {}", userId);
        SoundSettingsResponse response = userService.getSoundSettings(userId);
        return ResponseEntity.ok(ApiResponse.success(response, "소리 설정 조회 성공"));
    }

    // 소리 설정 수정
    @PutMapping("/bgm")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateSoundSettings(
            Authentication authentication,
            @RequestBody UpdateSoundSettingsRequest request) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("PUT 소리 설정 수정: {}", userId);
        UpdateUserResponse response = userService.updateSoundSettings(userId, request);
        return ResponseEntity.ok(ApiResponse.success(response, "소리 설정이 성공적으로 수정되었습니다."));
    }
}
