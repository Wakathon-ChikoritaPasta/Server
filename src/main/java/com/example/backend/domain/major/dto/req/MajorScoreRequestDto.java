package com.example.backend.domain.major.dto.req;

import com.example.backend.global.enums.MajorType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
