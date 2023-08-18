package com.example.backend.domain.user.req;

import com.example.backend.global.enums.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Getter @Setter
@Builder
public class UserRegisterRequestDto {

    private String username;
    private String password;
    private Major major;
}
