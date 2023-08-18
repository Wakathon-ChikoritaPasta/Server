package com.example.backend.domain.controller;

import com.example.backend.domain.ladybug.dto.req.UpdateLadyBugRequestDto;
import com.example.backend.domain.ladybug.dto.res.LadybugDetailResponseDto;
import com.example.backend.domain.ladybug.dto.res.UpdateLadyBugResponseDto;
import com.example.backend.domain.ladybug.service.LadybugService;
import com.example.backend.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ladybug")
public class LadybugController {
    private final LadybugService ladybugService;

    @GetMapping("/detail")
    public BaseResponseDto<LadybugDetailResponseDto> findLadybugDetailInfo(@RequestParam Long userId) {
        LadybugDetailResponseDto responseDto = ladybugService.findLadybugDetailInfo(userId);
        return new BaseResponseDto<>(responseDto);
    }

    @PostMapping
    public BaseResponseDto<UpdateLadyBugResponseDto> updateLadybugInfo(@RequestParam Long userId,
                                                                       @RequestBody UpdateLadyBugRequestDto requestDto) {
        UpdateLadyBugResponseDto responseDto = ladybugService.updateLadybugInfo(userId, requestDto);
        return new BaseResponseDto<>(responseDto);
    }

}
