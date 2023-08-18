package com.example.backend.domain.major.service;

import com.example.backend.domain.major.dto.MajorScoreRequestDto;
import com.example.backend.domain.major.dto.MajorScoreResponseDto;
import com.example.backend.domain.major.repository.MajorRepository;
import com.example.backend.global.enums.MajorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class MajorService {

    private final MajorRepository majorRepository;

    @Autowired
    public MajorService(MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }


    public List<String> getMajorNameList() {
        List<MajorType> majorNames = majorRepository.findAllMajorNames();
        List<String> majorTypes = new ArrayList<>();
        for (MajorType name : majorNames) {
            majorTypes.add(name.toString());
        }
        return majorTypes;
    }

    public List<Integer> getMajorScoreList() {
        return majorRepository.findAllMajorScores();
    }

    public List<MajorScoreResponseDto> getAllNamesAndTotalScores() {
        List<MajorScoreRequestDto> majorScoreRequestList = majorRepository.findAllNamesAndTotalScores();
        List<MajorScoreResponseDto> majorScoreResponseDtoList = new ArrayList<>();
        for (MajorScoreRequestDto majorScore : majorScoreRequestList) {
            MajorType name = majorScore.getName();
            MajorScoreResponseDto majorScoreResponseDto = MajorScoreResponseDto.of(name.toString(), majorScore.getTotalExperience());
            majorScoreResponseDtoList.add(majorScoreResponseDto);
        }
        return majorScoreResponseDtoList;
    }


    // 유저의 점수가 업데이트되었을 때 점수가 오른만큼 학과 점수에도 반영해줘야함

}
