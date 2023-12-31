package com.example.backend.domain.user.repository;

import com.example.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findAllByOrderByLevel_ExperienceAsc();

    Optional<User> findByKakaoId(Long kakaoId);
}
