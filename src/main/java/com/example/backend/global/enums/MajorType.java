package com.example.backend.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum MajorType {

    SOFTWARE("소프트웨어학과"),
    ARTIFICIAL_INTELLIGENCE("인공지능학과"),
    COMPUTER_ENGINEERING("컴퓨터공학과"),
    ELECTRONIC_ENGINEERING("전기공학과");

    private final String stringMajorType;

    public String toString() {
        return stringMajorType;
    }
}
