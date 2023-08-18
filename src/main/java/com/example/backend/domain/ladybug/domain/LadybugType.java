package com.example.backend.domain.ladybug.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LadybugType {
    EGG("봉인된 무당이"),
    PROSPECTIVE_FRESHMAN("예비 신입생 무당이"),
    FRESHMAN("새내기 무당이"),
    SECOND_GRADE("2학년 무당이"),
    THIRD_GRADE("3학년 무당이"),
    FORTH_GRADE("4학년 무당이");

    private final String stringMajorType;

    public String toString() {
        return stringMajorType;
    }

    public static LadybugType updateLadybugType(LadybugType currentLadybugType) {
        LadybugType[] types = LadybugType.values();
        for (int i = 0; i < types.length - 1; i++)
            if (types[i] == currentLadybugType)
                return types[i + 1];
        return null;
    }
}
