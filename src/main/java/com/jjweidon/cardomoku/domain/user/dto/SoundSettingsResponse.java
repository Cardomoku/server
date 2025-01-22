package com.jjweidon.cardomoku.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jjweidon.cardomoku.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SoundSettingsResponse {
    private boolean soundEffectsEnabled;
    private boolean backgroundMusicEnabled;

    public static SoundSettingsResponse from(User user) {
        return SoundSettingsResponse.builder()
                .soundEffectsEnabled(user.isSoundEffectsEnabled())
                .backgroundMusicEnabled(user.isBackgroundMusicEnabled())
                .build();
    }
}