package com.example.backend.domain.controller;

import com.example.backend.domain.level.dto.res.IndividualRankResponseDto;
import com.example.backend.domain.level.service.LevelService;
import com.example.backend.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/level")
public class LevelController {
    private final LevelService levelService;

    @GetMapping("/ranking")
    public BaseResponseDto<IndividualRankResponseDto> getRankingList() {
        IndividualRankResponseDto responseDto = levelService.getRankingList();
        return new BaseResponseDto<IndividualRankResponseDto>(responseDto);
    }
}
