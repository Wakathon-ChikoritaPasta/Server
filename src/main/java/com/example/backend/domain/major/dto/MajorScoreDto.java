package com.example.backend.domain.major.dto;

import lombok.*;

@Setter @Getter
@Builder
public class MajorScoreDto {
    private String name;
    private int totalScore;

    public MajorScoreDto(String name, int totalScore) {
        this.name = name;
        this.totalScore = totalScore;
    }
}
