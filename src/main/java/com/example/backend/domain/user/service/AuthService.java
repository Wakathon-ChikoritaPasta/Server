package com.example.backend.domain.user.service;

import com.example.backend.domain.user.domain.User;
import com.example.backend.domain.user.dto.req.TokenReissueRequestDto;
import com.example.backend.domain.user.dto.res.TokenReissueResponseDto;
import com.example.backend.domain.user.dto.res.UserLoginResponseDto;
import com.example.backend.domain.user.dto.res.UserRegisterResponseDto;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.enums.Authority;
import com.example.backend.global.enums.MajorType;
import com.example.backend.global.jwt.JwtTokenProvider;
import com.example.backend.global.jwt.TokenInfo;
import com.example.backend.global.response.BaseResponseDto;
import com.example.backend.global.response.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public BaseResponseDto<UserRegisterResponseDto> join(String username, String password, MajorType majorType) {

        User user = User.builder()
                .username(username)
                .roles(Collections.singletonList(Authority.USER.name()))
                .build();
        userRepository.save(user);

        return new BaseResponseDto<>(UserRegisterResponseDto.of(true));
    }

    @Transactional
    public BaseResponseDto<UserLoginResponseDto> login(String username, String password) {
        if (userRepository.findByUsername(username).orElse(null) == null) {
            return new BaseResponseDto<>(ErrorMessage.USER_NOT_FOUND);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenInfo.getRefreshToken()
                , tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return new BaseResponseDto<>(UserLoginResponseDto.of(tokenInfo.getAccessToken(), tokenInfo.getRefreshToken()));
    }

    @Transactional
    public BaseResponseDto<TokenReissueResponseDto> reissue(TokenReissueRequestDto reissue) {
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 정보가 유효하지 않습니다.");
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());

        if (!refreshToken.equals(reissue.getRefreshToken())) {
            return new BaseResponseDto<>(ErrorMessage.TOKEN_ERROR);
        }

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenInfo.getRefreshToken()
                , tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return new BaseResponseDto<>(TokenReissueResponseDto.of(tokenInfo.getAccessToken(), tokenInfo.getRefreshToken()));
    }
}
