package com.example.backend.domain.user.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
public class UserLoginResponseDto {

    private String accessToken;
    private String refreshToken;
}
