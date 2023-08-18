package com.example.backend.domain.controller;

import com.example.backend.domain.mission.dto.res.GetRandomMissionResponseDto;
import com.example.backend.domain.mission.service.MissionService;
import com.example.backend.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mission")
public class MissionController {
    private final MissionService missionService;
    @GetMapping
    public BaseResponseDto<GetRandomMissionResponseDto> getRandomMission(){
        GetRandomMissionResponseDto responseDto = missionService.getRandomMission();
        return new BaseResponseDto<>(responseDto);
    }
}
