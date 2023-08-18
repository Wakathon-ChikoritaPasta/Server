package com.example.backend.domain.level.repository;

import com.example.backend.domain.level.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {
}
