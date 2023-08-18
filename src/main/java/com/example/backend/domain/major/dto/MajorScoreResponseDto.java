package com.example.backend.domain.major.dto;

import com.example.backend.global.enums.MajorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class MajorScoreResponseDto {
    private String name;
    private Long totalExperience;
}
