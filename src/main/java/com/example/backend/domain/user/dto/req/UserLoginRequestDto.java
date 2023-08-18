package com.example.backend.domain.user.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Getter @Setter
@Builder
public class UserLoginRequestDto {

    private String username;
    private String password;
}
