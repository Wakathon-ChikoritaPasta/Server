package com.example.backend.domain.user.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Setter @Getter
@Builder
public class TokenReissueRequestDto {

    private String accessToken;
    private String refreshToken;
}
