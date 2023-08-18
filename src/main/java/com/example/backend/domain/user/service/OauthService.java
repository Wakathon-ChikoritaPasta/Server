package com.example.backend.domain.user.service;

import com.example.backend.domain.user.domain.User;
import com.example.backend.domain.user.dto.res.KakaoLoginResponseDto;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.jwt.JwtTokenProvider;
import com.example.backend.global.jwt.TokenInfo;
import com.example.backend.global.oauth.KakaoUserInfo;
import com.example.backend.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OauthService {

    private static final String BEARER_TYPE = "Bearer";
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BaseResponseDto<KakaoLoginResponseDto> kakaoLogin(String kakaoAccessToken) {
        boolean isNew = false;

        User user = checkUserExist(kakaoAccessToken);
        if (user == null) {
            isNew = true;
            Map<String, Object> userAttributesByToken = getUserAttributesByToken(kakaoAccessToken);
            user = User.builder()
                    .kakaoId((Long) userAttributesByToken.get("id"))
                    .username("username")
                    .password(passwordEncoder.encode("password"))
                    .roles(Collections.singletonList("USER"))
                    .build();

            userRepository.save(user);
        }

//        log.info("kakao-login : 1");
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken("username", "password");
//
//        log.info("kakao-login : 2");
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        log.info("kakao-login : 3");
//        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
//
//        log.info("kakao-login : 4");
//        redisTemplate.opsForValue().set("RT:" + user.getUsername(), tokenInfo.getRefreshToken()
//                , tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        TokenInfo tokenInfo = new TokenInfo("Bearer", "accessToken", "refreshToken", 1000L);

        return new BaseResponseDto<>(KakaoLoginResponseDto.of(
                user.getId(), isNew, tokenInfo.getAccessToken(), tokenInfo.getRefreshToken()));
    }

    private Map<String, Object> getUserAttributesByToken(String accessToken){
        return WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private User checkUserExist(String accessToken){

        Map<String, Object> userAttributesByToken = getUserAttributesByToken(accessToken);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userAttributesByToken);
        Long kakaoId = kakaoUserInfo.getId();
        Optional<User> user = userRepository.findByKakaoId(kakaoId);
        return user.orElse(null);
    }
}
