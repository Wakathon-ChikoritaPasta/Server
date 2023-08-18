package com.example.backend.domain.controller;

import com.example.backend.domain.ladybug.dto.res.LadybugDetailResponseDto;
import com.example.backend.domain.ladybug.service.LadybugService;
import com.example.backend.domain.user.domain.User;
import com.example.backend.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ladybug")
public class LadybugController {
    private final LadybugService ladybugService;

    @GetMapping("/detail")
    public BaseResponseDto<LadybugDetailResponseDto> findLadybugDetailInfo(@AuthenticationPrincipal User user){
        LadybugDetailResponseDto responseDto = ladybugService.findLadybugDetailInfo(user);
        return new BaseResponseDto<LadybugDetailResponseDto>(responseDto);
    }

}
