package com.example.backend.domain.major.dto.res;

import com.example.backend.global.enums.MajorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class BaseMajorRankResponseDto {
    private int rank;
    private String majorType;
    private Long experience;
}
