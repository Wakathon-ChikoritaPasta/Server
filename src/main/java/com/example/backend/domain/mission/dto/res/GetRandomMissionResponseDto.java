package com.example.backend.domain.mission.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class GetRandomMissionResponseDto {
    private List<BaseMissionResponseDto> missionList;
}
