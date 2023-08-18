package com.example.backend.domain.level.dto.res;

import com.example.backend.domain.ladybug.domain.LadybugType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class BaseLevelResponseDto {
    private int level;
    private long experience;
    private String ladybugType;
}
