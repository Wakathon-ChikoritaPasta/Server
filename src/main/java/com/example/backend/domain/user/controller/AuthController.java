package com.example.backend.domain.user.controller;

import com.example.backend.global.response.BaseResponseDto;
import com.example.backend.domain.user.service.AuthService;
import com.example.backend.domain.user.dto.req.TokenReissueRequestDto;
import com.example.backend.domain.user.dto.req.UserLoginRequestDto;
import com.example.backend.domain.user.dto.req.UserRegisterRequestDto;
import com.example.backend.domain.user.dto.res.TokenReissueResponseDto;
import com.example.backend.domain.user.dto.res.UserLoginResponseDto;
import com.example.backend.domain.user.dto.res.UserRegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public BaseResponseDto<UserRegisterResponseDto> join(
            @RequestBody UserRegisterRequestDto request
    ) {
        return authService.join(request.getUsername(), request.getPassword(), request.getMajor());
    }

    @PostMapping("/login")
    public BaseResponseDto<UserLoginResponseDto> login(
            @RequestBody UserLoginRequestDto request
    ) {
        return authService.login(request.getUsername(), request.getPassword());
    }

    @PostMapping("/reissue")
    public BaseResponseDto<TokenReissueResponseDto> reissue(
            @RequestBody TokenReissueRequestDto request
    ) {
        return authService.reissue(request);
    }
}
