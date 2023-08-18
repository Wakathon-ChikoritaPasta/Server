package com.example.backend.domain.ladybug.dto.res;

import com.example.backend.domain.level.dto.res.BaseIndividualRankResponseDto;
import com.example.backend.domain.level.dto.res.BaseLevelResponseDto;
import com.example.backend.domain.major.dto.res.BaseMajorRankResponseDto;
import com.example.backend.domain.symbol.domain.SymbolType;
import com.example.backend.global.enums.MajorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class LadybugDetailResponseDto {
    private String symbol;
    private String majorType;
    private BaseLevelResponseDto levelInfo;
    private List<BaseIndividualRankResponseDto> schoolRank;
    private List<BaseMajorRankResponseDto> majorRank;
}
