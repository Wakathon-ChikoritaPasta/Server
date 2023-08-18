package com.example.backend.domain.ladybug.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LadybugType {
    EGG,
    PROSPECTIVE_FRESHMAN,
    FRESHMAN,
    SECOND_GRADE,
    THIRD_GRADE,
    FORTH_GRADE;

    public static LadybugType updateLadybugType(LadybugType currentLadybugType) {
        LadybugType[] types = LadybugType.values();
        for (int i = 0; i < types.length - 1; i++)
            if (types[i] == currentLadybugType)
                return types[i + 1];
        return null;
    }
}
