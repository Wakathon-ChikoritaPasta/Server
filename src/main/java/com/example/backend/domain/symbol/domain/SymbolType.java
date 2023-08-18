package com.example.backend.domain.symbol.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SymbolType {
    BRONZE("브론즈"),
    SILVER("실버"),
    GOLD("골드"),
    PLATINUM("플레티넘"),
    DIAMOND("다이아몬드");

    private final String stringSymbolType;
    public String toString() {
        return stringSymbolType;
    }

    public static SymbolType updateSymbolType(SymbolType currentSymbolType) {
        SymbolType[] types = SymbolType.values();
        for (int i = 0; i < types.length - 1; i++)
            if (types[i] == currentSymbolType)
                return types[i + 1];
        return null;
    }
}
