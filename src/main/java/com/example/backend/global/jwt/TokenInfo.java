package com.example.backend.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
@Getter
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private long refreshTokenExpirationTime;
}
