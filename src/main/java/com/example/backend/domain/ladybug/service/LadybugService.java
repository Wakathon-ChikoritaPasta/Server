package com.example.backend.domain.ladybug.service;

import com.example.backend.domain.ladybug.domain.LadybugType;
import com.example.backend.domain.ladybug.dto.req.UpdateLadyBugRequestDto;
import com.example.backend.domain.ladybug.dto.res.LadybugDetailResponseDto;
import com.example.backend.domain.ladybug.dto.res.UpdateLadyBugResponseDto;
import com.example.backend.domain.level.domain.Level;
import com.example.backend.domain.level.dto.res.BaseIndividualRankResponseDto;
import com.example.backend.domain.level.dto.res.BaseLevelResponseDto;
import com.example.backend.domain.major.domain.Major;
import com.example.backend.domain.major.dto.res.BaseMajorRankResponseDto;
import com.example.backend.domain.major.repository.MajorRepository;
import com.example.backend.domain.mission.domain.Mission;
import com.example.backend.domain.mission.repository.MissionRepository;
import com.example.backend.domain.symbol.domain.SymbolType;
import com.example.backend.domain.user.domain.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.enums.MajorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@RequiredArgsConstructor
@Service
public class LadybugService {
    private final UserRepository userRepository;
    private final MajorRepository majorRepository;
    private final MissionRepository missionRepository;

    public LadybugDetailResponseDto findLadybugDetailInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        SymbolType symbolType = getSymbolTypeFromUser(user);
        MajorType userMajorType = getMajorFromUser(user);
        BaseLevelResponseDto baseLevelResponseDto = getBaseLevelInfoFromUser(user);
        List<BaseIndividualRankResponseDto> schoolRank = getSchoolRankFromUser(user);
        List<BaseMajorRankResponseDto> majorRank = getMajorRankFromUser(user);
        return LadybugDetailResponseDto.of(symbolType.toString(), userMajorType.toString(), baseLevelResponseDto, schoolRank, majorRank);
    }

    public UpdateLadyBugResponseDto updateLadybugInfo(Long userId, UpdateLadyBugRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Long> successMissions = updateLadybugExperienceForMission(user, requestDto.getMissionIdList(), requestDto.getLongitude(), requestDto.getLatitude());
        return UpdateLadyBugResponseDto.of(successMissions);
    }

    private List<Long> updateLadybugExperienceForMission(User user, List<Long> missionList, double longitude, double latitude) {
        List<Long> successMission = new ArrayList<>();
        missionList.forEach(missionId -> {
            Mission mission = getMission(missionId);
            double distance = calculateDistance(mission.getLongitude(), mission.getLatitude(), longitude, latitude);
            Long successId = updateExperienceForMission(user, mission, distance);
            if (!Objects.isNull(successId))
                successMission.add(successId);
        });
        return successMission;
    }

    private Mission getMission(Long missionId) {
        return missionRepository.findById(missionId).orElseThrow();
    }

    private double calculateDistance(double firstLongitude, double firstLatitude, double secondLongitude, double secondLatitude) {
        final int R = 6371; // 지구의 반경 (단위: km)
        double latDistance = Math.toRadians(secondLatitude - firstLatitude);
        double lonDistance = Math.toRadians(Math.abs(secondLongitude - firstLongitude)); // 절댓값 사용
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(firstLatitude)) * Math.cos(Math.toRadians(secondLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000;
    }

    private Long updateExperienceForMission(User user, Mission mission, double distance) {
        if (distance > 100) return null;
        int reward = mission.getRewards();
        Level level = user.getLevel();
        level.updateExperience(reward);
        level.updateLevel();
        if (level.getLevel() % 3 == 0)
            level.updateLadybugType();
        user.updateCount();
        user.getSymbol().updateSymbolType();
        user.getMajor().updateTotalExperience((long) reward);
        return mission.getId();
    }

    private BaseLevelResponseDto getBaseLevelInfoFromUser(User user) {
        Level level = user.getLevel();
        return BaseLevelResponseDto.of(level.getLevel(), level.getExperience(), level.getLadybugType().toString());
    }

    private List<BaseIndividualRankResponseDto> getSchoolRankFromUser(User user) {
        List<BaseIndividualRankResponseDto> baseIndividualRankResponseDtoList = new ArrayList<>();
        List<User> usersSortedByLevel = getUsersSortedByLevel();
        int criteria = getUserIndividualRank(usersSortedByLevel, user);
        for (int i = -1; i < 2; i++) {
            if (!validateIndex(criteria + i, usersSortedByLevel.size()))
                continue;
            User currentUser = usersSortedByLevel.get(criteria + i);
            addBaseIndividualRankResponseDtoList(baseIndividualRankResponseDtoList, currentUser, criteria + i);
        }
        return baseIndividualRankResponseDtoList;
    }

    private void addBaseIndividualRankResponseDtoList(List<BaseIndividualRankResponseDto> baseIndividualRankResponseDtoList, User user, int rank) {
        BaseIndividualRankResponseDto baseIndividualRankResponseDto = createBaseIndividualRankResponseDto(user, rank + 1);
        baseIndividualRankResponseDtoList.add(baseIndividualRankResponseDto);
    }

    private BaseIndividualRankResponseDto createBaseIndividualRankResponseDto(User user, int rank) {
        String nickname = user.getUsername();
        LadybugType ladybugType = user.getLevel().getLadybugType();
        Long experience = user.getLevel().getExperience();
        return BaseIndividualRankResponseDto.of(rank, nickname, ladybugType.toString(), experience);
    }

    private List<BaseMajorRankResponseDto> getMajorRankFromUser(User user) {
        List<BaseMajorRankResponseDto> baseMajorRankResponseDtoList = new ArrayList<>();
        List<Major> majors = majorRepository.findAllByOrderByTotalExperienceDesc();
        int criteria = getMajorRank(majors, user.getMajor());
        for (int i = -1; i < 2; i++) {
            if (!validateIndex(criteria + i, majors.size()))
                continue;
            Major major = majors.get(criteria + i);
            addBaseMajorRankResponseDtoList(baseMajorRankResponseDtoList, major, criteria + i);
        }
        return baseMajorRankResponseDtoList;
    }

    private void addBaseMajorRankResponseDtoList(List<BaseMajorRankResponseDto> baseMajorRankResponseDtoList, Major major, int rank) {
        BaseMajorRankResponseDto baseMajorRankResponseDto = createBaseMajorRankResponseDto(major, rank + 1);
        baseMajorRankResponseDtoList.add(baseMajorRankResponseDto);
    }

    private BaseMajorRankResponseDto createBaseMajorRankResponseDto(Major major, int rank) {
        return BaseMajorRankResponseDto.of(rank, major.getName().toString(), major.getTotalExperience());
    }

    private SymbolType getSymbolTypeFromUser(User user) {
        return user.getSymbol().getTitle();
    }

    private MajorType getMajorFromUser(User user) {
        return user.getMajor().getName();
    }

    private List<User> getUsersSortedByLevel() {
        return userRepository.findAllByOrderByLevel_ExperienceAsc();
    }

    private int getUserIndividualRank(List<User> usersSortedByLevel, User user) {
        return usersSortedByLevel.indexOf(user);
    }

    private int getMajorRank(List<Major> majors, Major major) {
        return majors.indexOf(major);
    }

    private boolean validateIndex(int index, int endPoint) {
        if (index < 0 || index >= endPoint)
            return false;
        return true;
    }
}
