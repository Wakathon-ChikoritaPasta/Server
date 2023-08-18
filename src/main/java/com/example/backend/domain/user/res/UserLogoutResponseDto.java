package com.example.backend.domain.user.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Setter @Getter
@Builder
public class UserLogoutResponseDto {

    private Boolean isSucceed;
}
