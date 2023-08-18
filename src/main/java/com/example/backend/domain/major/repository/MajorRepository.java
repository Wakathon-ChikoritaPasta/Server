package com.example.backend.domain.major.repository;

import com.example.backend.domain.major.domain.Major;
import com.example.backend.domain.major.dto.req.MajorScoreRequestDto;
import com.example.backend.global.enums.MajorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long> {

    @Query("SELECT m.name FROM Major m")
    List<MajorType> findAllMajorNames();

    @Query("SELECT m.totalExperience FROM Major m")
    List<Integer> findAllMajorScores();

    @Query("SELECT new com.example.backend.domain.major.dto.MajorScoreRequestDto(m.name, m.totalExperience) FROM Major m")
    List<MajorScoreRequestDto> findAllNamesAndTotalScores();

    List<Major> findAllByOrderByTotalExperienceDesc();

    Optional<Major> findMajorByName(MajorType name);
}
