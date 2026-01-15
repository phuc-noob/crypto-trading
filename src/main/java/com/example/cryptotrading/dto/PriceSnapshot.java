package com.example.cryptotrading.dto;

import java.math.BigDecimal;

public record PriceSnapshot(
        String symbol,
        BigDecimal bid,
        BigDecimal ask
) {}
