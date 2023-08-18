package com.example.backend.domain.user.service;

import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.response.BaseResponseDto;
import com.example.backend.global.response.ErrorMessage;
import com.example.backend.domain.user.dto.req.UserLogoutRequestDto;
import com.example.backend.domain.user.dto.res.UserLogoutResponseDto;
import com.example.backend.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

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
}
