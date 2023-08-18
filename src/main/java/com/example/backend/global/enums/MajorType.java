package com.example.backend.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MajorType {

    SOFTWARE("소프트웨어학과"),
    ARTIFICIAL_INTELLIGENCE("인공지능학과"),
    COMPUTER_ENGINEERING("컴퓨터공학과"),
    ELECTRONIC_ENGINEERING("전기공학과"),
    INDUSTRY_ENGINEERING("산업공학과"),
    PHYSICS("물리학과"),
    BIOLOGY_ENGINEERING("생명공학과"),
    CHEMISTRY("화학과"),
    PSYCHOLOGY("심리학과"),
    MATHEMATICS("수학과"),
    HISTORY("역사학과"),
    CHEMICAL_ENGINEERING("화학공학과"),
    SOCIOLOGY("사회학과"),
    LAW("법학과"),
    MEDICAL_ENGINEERING("의료공학과"),
    EDUCATION("교육학과");

    private final String stringMajorType;

    public String toString() {
        return stringMajorType;
    }
}
