package com.example.cryptotrading.service;

import java.util.Set;

public final class TradingPairFactory {

    private static final Set<String> SUPPORTED =
            Set.of("BTCUSDT", "ETHUSDT");

    public static boolean isSupported(String symbol) {
        return SUPPORTED.contains(symbol);
    }

    public static Set<String> supportedPairs() {
        return SUPPORTED;
    }
}