package com.example.backend.domain.user.dto.req;

import com.example.backend.domain.major.domain.Major;
import com.example.backend.global.enums.MajorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class UserUpdateRequestDto {

    private final Long userId;
    private final MajorType newMajor;
    private final String newUsername;
}
