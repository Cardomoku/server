package com.jjweidon.cardomoku.domain.user.controller;

import com.jjweidon.cardomoku.domain.user.dto.CustomUserDetails;
import com.jjweidon.cardomoku.domain.user.dto.UpdateUserProfileRequest;
import com.jjweidon.cardomoku.domain.user.dto.UserProfileResponse;
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
    public ResponseEntity<ApiResponse<Void>> updateProfile(
            Authentication authentication,
            @RequestBody UpdateUserProfileRequest request) {
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        UserProfileResponse response = userService.updateProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success(U, "프로필이 성공적으로 수정되었습니다."));
    }
}
