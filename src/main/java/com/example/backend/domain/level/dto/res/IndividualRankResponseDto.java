package com.example.backend.domain.level.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class IndividualRankResponseDto {
    private List<BaseIndividualRankResponseDto> rankingList;
}
