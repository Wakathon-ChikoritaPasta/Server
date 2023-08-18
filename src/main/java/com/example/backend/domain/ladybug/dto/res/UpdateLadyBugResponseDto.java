package com.example.backend.domain.ladybug.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class UpdateLadyBugResponseDto {
    private List<Long> successMission;
}
