package com.example.backend.domain.controller;

import com.example.backend.domain.response.BaseResponseDto;
import com.example.backend.domain.user.AuthService;
import com.example.backend.domain.user.req.TokenReissueRequestDto;
import com.example.backend.domain.user.req.UserLoginRequestDto;
import com.example.backend.domain.user.req.UserRegisterRequestDto;
import com.example.backend.domain.user.res.TokenReissueResponseDto;
import com.example.backend.domain.user.res.UserLoginResponseDto;
import com.example.backend.domain.user.res.UserRegisterResponseDto;
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
