package com.jjweidon.cardomoku.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserProfileRequest {
    private String nickname;
    private String image;
}
