package com.example.backend.domain.level.dto.res;

import com.example.backend.domain.ladybug.domain.LadybugType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class BaseIndividualRankResponseDto {
    private int rank;
    private String nickname;
    private String ladybugType;
    private Long experience;
}
