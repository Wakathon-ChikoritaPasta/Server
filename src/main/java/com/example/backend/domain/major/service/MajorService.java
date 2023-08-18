package com.example.backend.domain.major.service;

import com.example.backend.domain.major.dto.MajorScoreDto;
import com.example.backend.domain.major.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {

    private final MajorRepository majorRepository;

    @Autowired
    public MajorService(MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }


    public List<String> getMajorNameList() {
        return majorRepository.findAllMajorNames();
    }

    public List<Integer> getMajorScoreList() {
        return majorRepository.findAllMajorScores();
    }

    public List<MajorScoreDto> getAllNamesAndTotalScores() {
        return majorRepository.findAllNamesAndTotalScores();
    }

    // 유저의 점수가 업데이트되었을 때 점수가 오른만큼 학과 점수에도 반영해줘야함

}
