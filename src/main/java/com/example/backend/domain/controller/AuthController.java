package com.example.backend.domain.controller;

import com.example.backend.domain.user.dto.req.TokenReissueRequestDto;
import com.example.backend.domain.user.dto.req.UserLoginRequestDto;
import com.example.backend.domain.user.dto.req.UserRegisterRequestDto;
import com.example.backend.domain.user.dto.res.KakaoLoginResponseDto;
import com.example.backend.domain.user.dto.res.TokenReissueResponseDto;
import com.example.backend.domain.user.dto.res.UserLoginResponseDto;
import com.example.backend.domain.user.dto.res.UserRegisterResponseDto;
import com.example.backend.domain.user.service.AuthService;
import com.example.backend.domain.user.service.OauthService;
import com.example.backend.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final OauthService oauthService;

    @PostMapping("/join")
    public BaseResponseDto<UserRegisterResponseDto> join(
            @RequestBody UserRegisterRequestDto request
    ) {
        return authService.join(request.getUsername(), request.getPassword(), request.getMajorType());
    }

    @PostMapping("/login")
    public BaseResponseDto<UserLoginResponseDto> login(
            @RequestBody UserLoginRequestDto request
    ) {
        return authService.login(request.getUsername(), request.getPassword());
    }

    @PostMapping("/kakao-login")
    public BaseResponseDto<KakaoLoginResponseDto> kakaoLogin(
            @RequestParam String accessToken
    ) {
        return oauthService.kakaoLogin(accessToken);
    }

    @PostMapping("/reissue")
    public BaseResponseDto<TokenReissueResponseDto> reissue(
            @RequestBody TokenReissueRequestDto request
    ) {
        return authService.reissue(request);
    }
}
