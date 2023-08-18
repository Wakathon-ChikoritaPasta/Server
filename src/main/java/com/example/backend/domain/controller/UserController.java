package com.example.backend.domain.controller;

import com.example.backend.domain.user.domain.User;
import com.example.backend.domain.user.dto.req.UserUpdateRequestDto;
import com.example.backend.domain.user.dto.res.UserUpdateResponseDto;
import com.example.backend.domain.user.service.UserService;
import com.example.backend.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PatchMapping("/update")
    public BaseResponseDto<UserUpdateResponseDto> setUserInfo(
            @RequestBody UserUpdateRequestDto newUserInfo
    ) {
        return userService.setUserInfo
                (newUserInfo.getUserId(), newUserInfo.getNewUsername(), newUserInfo.getNewMajor());
    }
}
