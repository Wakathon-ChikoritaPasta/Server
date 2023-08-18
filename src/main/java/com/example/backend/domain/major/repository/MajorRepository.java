package com.example.backend.domain.major.repository;

import com.example.backend.domain.major.domain.Major;
import com.example.backend.domain.major.dto.MajorScoreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {

    @Query("SELECT m.name FROM Major m")
    List<String> findAllMajorNames();

    @Query("SELECT m.total_score FROM Major m")
    List<Integer> findAllMajorScores();

    @Query("SELECT new com.example.backend.domain.major.dto.MajorScoreDto(m.name, m.total_score) FROM Major m")
    List<MajorScoreDto> findAllNamesAndTotalScores();

    List<Major> findAllByOrderByTotalExperienceAsc();
}
