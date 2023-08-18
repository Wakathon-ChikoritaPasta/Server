package com.example.backend.domain.ladybug.service;

import com.example.backend.domain.ladybug.domain.LadybugType;
import com.example.backend.domain.ladybug.dto.res.LadybugDetailResponseDto;
import com.example.backend.domain.level.domain.Level;
import com.example.backend.domain.level.dto.res.BaseIndividualRankResponseDto;
import com.example.backend.domain.level.dto.res.BaseLevelResponseDto;
import com.example.backend.domain.level.repository.LevelRepository;
import com.example.backend.domain.major.domain.Major;
import com.example.backend.domain.major.dto.res.BaseMajorRankResponseDto;
import com.example.backend.domain.major.repository.MajorRepository;
import com.example.backend.domain.symbol.domain.SymbolType;
import com.example.backend.domain.user.domain.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.enums.MajorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LadybugService {
    private final UserRepository userRepository;
    private final MajorRepository majorRepository;

    public LadybugDetailResponseDto findLadybugDetailInfo(User user){
        SymbolType symbolType = getSymbolTypeFromUser(user);
        MajorType userMajorType = getMajorFromUser(user);
        BaseLevelResponseDto baseLevelResponseDto = getBaseLevelInfoFromUser(user);
        List<BaseIndividualRankResponseDto> schoolRank = getSchoolRankFromUser(user);
        List<BaseMajorRankResponseDto> majorRank = getMajorRankFromUser(user);
        return LadybugDetailResponseDto.of(symbolType, userMajorType, baseLevelResponseDto, schoolRank, majorRank);
    }
    private BaseLevelResponseDto getBaseLevelInfoFromUser(User user){
        Level level = user.getLevel();
        return BaseLevelResponseDto.of(level.getLevel(), level.getExperience(), level.getLadybugType());
    }
    private List<BaseIndividualRankResponseDto> getSchoolRankFromUser(User user){
        List<BaseIndividualRankResponseDto> baseIndividualRankResponseDtoList = new ArrayList<>();
        List<User> usersSortedByLevel = getUsersSortedByLevel();
        int criteria = getUserIndividualRank(usersSortedByLevel, user);
        for(int i = -1; i < 2; i++){
            User currentUser = usersSortedByLevel.get(criteria + i);
            addBaseIndividualRankResponseDtoList(baseIndividualRankResponseDtoList, currentUser, criteria + i);
        }
        return baseIndividualRankResponseDtoList;
    }
    private void addBaseIndividualRankResponseDtoList(List<BaseIndividualRankResponseDto> baseIndividualRankResponseDtoList, User user, int rank){
        BaseIndividualRankResponseDto baseIndividualRankResponseDto = createBaseIndividualRankResponseDto(user, rank);
        baseIndividualRankResponseDtoList.add(baseIndividualRankResponseDto);
    }
    private BaseIndividualRankResponseDto createBaseIndividualRankResponseDto(User user, int rank){
        String nickname = user.getUsername();
        LadybugType ladybugType = user.getLevel().getLadybugType();
        Long experience = user.getLevel().getExperience();
        return BaseIndividualRankResponseDto.of(rank, nickname, ladybugType, experience);
    }
    private List<BaseMajorRankResponseDto> getMajorRankFromUser(User user){
        List<BaseMajorRankResponseDto> baseMajorRankResponseDtoList = new ArrayList<>();
        List<Major> majors = majorRepository.findAllByOrderByTotalExperienceAsc();
        int criteria = getMajorRank(majors, user.getMajor());
        for(int i = -1; i < 2; i++){
            Major major = majors.get(criteria + i);
            addBaseMajorRankResponseDtoList(baseMajorRankResponseDtoList, major, criteria + i);
        }
        return baseMajorRankResponseDtoList;
    }

    private void addBaseMajorRankResponseDtoList(List<BaseMajorRankResponseDto> baseMajorRankResponseDtoList, Major major, int rank){
        BaseMajorRankResponseDto baseMajorRankResponseDto = createBaseMajorRankResponseDto(major, rank);
        baseMajorRankResponseDtoList.add(baseMajorRankResponseDto);
    }

    private BaseMajorRankResponseDto createBaseMajorRankResponseDto(Major major, int rank){
        return BaseMajorRankResponseDto.of(rank, major.getName(), major.getTotalExperience());
    }
    private SymbolType getSymbolTypeFromUser(User user){
        return user.getSymbol().getTitle();
    }
    private MajorType getMajorFromUser(User user){
        return user.getMajor().getName();
    }
    private List<User> getUsersSortedByLevel(){
        return userRepository.findAllByOrderByLevel_ExperienceAsc();
    }
    private int getUserIndividualRank(List<User> usersSortedByLevel, User user){
        return usersSortedByLevel.indexOf(user);
    }
    private int getMajorRank(List<Major> majors, Major major){
        return majors.indexOf(major);
    }
}
