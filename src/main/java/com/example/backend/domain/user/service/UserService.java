package com.example.backend.domain.user.service;

import com.example.backend.domain.major.domain.Major;
import com.example.backend.domain.major.repository.MajorRepository;
import com.example.backend.domain.user.domain.User;
import com.example.backend.domain.user.dto.req.UserLogoutRequestDto;
import com.example.backend.domain.user.dto.res.UserLogoutResponseDto;
import com.example.backend.domain.user.dto.res.UserUpdateResponseDto;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.enums.MajorType;
import com.example.backend.global.jwt.JwtTokenProvider;
import com.example.backend.global.response.BaseResponseDto;
import com.example.backend.global.response.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MajorRepository majorRepository;

    @Transactional
    public BaseResponseDto<UserLogoutResponseDto> logout(UserLogoutRequestDto request) {

        if (!jwtTokenProvider.validateToken(request.getAccessToken())) {
            return new BaseResponseDto<>(ErrorMessage.TOKEN_ERROR);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(request.getAccessToken());

        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }

        Long expiration = jwtTokenProvider.getExpiration(request.getAccessToken());
        redisTemplate.opsForValue().set(request.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        log.info("UserService.logout: logout success");
        return new BaseResponseDto<>(UserLogoutResponseDto.of(true));
    }

    @Transactional
    public BaseResponseDto<UserUpdateResponseDto> setUserInfo(Long userId, String newUsername, MajorType newMajor) {
        log.info("UserService.setUserInfo: newUsername: {}, newMajor: {}", newUsername, newMajor);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new BaseResponseDto<>(UserUpdateResponseDto.of(false));
        }

        log.info("UserService.setUserInfo: user: {}", user);
        user.setUsername(newUsername);
        Optional<Major> major = majorRepository.findMajorByName(newMajor);
        if (major.isPresent()) {
            major.get().addMajorWithUser(major.get(), user);
        } else {
            return new BaseResponseDto<>(UserUpdateResponseDto.of(false));
        }

        log.info("UserService.setUserInfo: user: {}", user);
        userRepository.save(user);
        return new BaseResponseDto<>(UserUpdateResponseDto.of(true));
    }
}
