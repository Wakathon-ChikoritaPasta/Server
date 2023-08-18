package com.example.backend.domain.major.dto;

import com.example.backend.global.enums.MajorType;
import lombok.*;

@Setter @Getter
@Builder
public class MajorScoreRequestDto {
    private MajorType name;
    private Long totalExperience;

    public MajorScoreRequestDto(MajorType name, Long totalExperience) {
        this.name = name;
        this.totalExperience = totalExperience;
    }
}
