package com.example.backend.domain.level.service;

import com.example.backend.domain.ladybug.domain.LadybugType;
import com.example.backend.domain.level.dto.res.BaseIndividualRankResponseDto;
import com.example.backend.domain.level.dto.res.IndividualRankResponseDto;
import com.example.backend.domain.user.domain.User;
import com.example.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LevelService {

    private final UserRepository userRepository;

    public IndividualRankResponseDto getRankingList() {
        List<User> usersSortedByLevel = getUsersSortedByLevel();
        List<BaseIndividualRankResponseDto> baseIndividualRankResponseDtoList = new ArrayList<>();
        for (int i = 0; i < usersSortedByLevel.size(); i++) {
            User user = usersSortedByLevel.get(i);
            BaseIndividualRankResponseDto baseIndividualRankResponseDto = createBaseIndividualRankResponseDto(user, i + 1);
            baseIndividualRankResponseDtoList.add(baseIndividualRankResponseDto);
        }
        return IndividualRankResponseDto.of(baseIndividualRankResponseDtoList);
    }

    private BaseIndividualRankResponseDto createBaseIndividualRankResponseDto(User user, int rank) {
        String nickname = user.getUsername();
        LadybugType ladybugType = user.getLevel().getLadybugType();
        Long experience = user.getLevel().getExperience();
        return BaseIndividualRankResponseDto.of(rank, nickname, ladybugType, experience);
    }

    private List<User> getUsersSortedByLevel() {
        return userRepository.findAllByOrderByLevel_ExperienceAsc();
    }


}
