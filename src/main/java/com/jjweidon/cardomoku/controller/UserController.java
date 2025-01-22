package com.jjweidon.cardomoku.controller;

import com.jjweidon.cardomoku.dto.CustomUserDetails;
import com.jjweidon.cardomoku.dto.UserProfileResponse;
import com.jjweidon.cardomoku.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
