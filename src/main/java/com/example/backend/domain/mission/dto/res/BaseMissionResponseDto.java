package com.example.backend.domain.mission.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class BaseMissionResponseDto {
    private final String missionName;
}
