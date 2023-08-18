package com.example.backend.domain.controller;

import com.example.backend.domain.major.dto.req.MajorScoreResponseDto;
import com.example.backend.domain.major.service.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/major")
public class MajorController {

    private final MajorService majorService;

    @GetMapping("/all")
    public List<MajorScoreResponseDto> getAll() {
        return majorService.getAllNamesAndTotalScores();
    }

    @GetMapping("/names")
    public List<String> getMajorNameList() {
        return majorService.getMajorNameList();
    }
}
