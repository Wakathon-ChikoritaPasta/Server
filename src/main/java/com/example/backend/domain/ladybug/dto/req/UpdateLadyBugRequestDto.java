package com.example.backend.domain.ladybug.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateLadyBugRequestDto {
    private double latitude;
    private double longitude;
    private List<Long> missionIdList;

}
