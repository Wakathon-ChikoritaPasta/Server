package com.example.backend.domain.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor(staticName = "of")
@Builder
public class KakaoLoginResponseDto {

    private Long userId;
    private boolean isNewUser;
    private String accessToken;
    private String refreshToken;
}
