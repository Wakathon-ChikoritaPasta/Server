package com.example.backend.domain.symbol.domain;

import com.example.backend.domain.ladybug.domain.LadybugType;

public enum SymbolType {
    BRONZE,
    SILVER,
    GOLD,
    PLATINUM,
    DIAMOND;

    public static SymbolType updateSymbolType(SymbolType currentSymbolType) {
        SymbolType[] types = SymbolType.values();
        for (int i = 0; i < types.length - 1; i++)
            if (types[i] == currentSymbolType)
                return types[i + 1];
        return null;
    }
}
