package com.example.backend.domain.mission.service;

import com.example.backend.domain.major.repository.MajorRepository;
import com.example.backend.domain.mission.domain.Mission;
import com.example.backend.domain.mission.dto.res.BaseMissionResponseDto;
import com.example.backend.domain.mission.dto.res.GetRandomMissionResponseDto;
import com.example.backend.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Transactional
@RequiredArgsConstructor
@Service
public class MissionService {
    private final MissionRepository missionRepository;
    public GetRandomMissionResponseDto getRandomMission(){
        List<BaseMissionResponseDto> baseMissionResponseDtoList = createBaseMissionResponseDtoList();
        System.out.println("check");
        return GetRandomMissionResponseDto.of(baseMissionResponseDtoList);
    }
    private List<BaseMissionResponseDto> createBaseMissionResponseDtoList(){
        List<BaseMissionResponseDto> baseMissionResponseDtoList = new ArrayList<>();
        getRandomMissionId().forEach(id -> {
            System.out.println(id);
            Mission mission = getMission(id);
            BaseMissionResponseDto baseMissionResponseDto = createBaseMissionResponseDto(mission);
            baseMissionResponseDtoList.add(baseMissionResponseDto);
        });
        return baseMissionResponseDtoList;
    }
    private Mission getMission(Long missionId){
        return missionRepository.findById(missionId).orElseThrow();
    }
    private BaseMissionResponseDto createBaseMissionResponseDto(Mission mission){
        return BaseMissionResponseDto.of(mission.getId(), mission.getTitle(), mission.getLatitude(), mission.getLongitude());
    }
    private List<Mission> getAllMissionList(){
        return missionRepository.findAll();
    }
    private List<Long> getRandomMissionId(){
        List<Mission> allMissionList = getAllMissionList();
        return generateUniqueRandomLongs(allMissionList.size(), 3);
    }

    private List<Long> generateUniqueRandomLongs(int max, int count) {
        Random random = new Random();
        List<Long> uniqueValues = new ArrayList<>();
        while (uniqueValues.size() < count) {
            int randomNumber = random.nextInt(max)+1; // 0부터 maxValue까지의 랜덤 값
            if (randomNumber >= 0 && !uniqueValues.contains((long) randomNumber)) {
                uniqueValues.add((long)randomNumber);
            }
        }
        return uniqueValues;
    }
}
