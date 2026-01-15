package com.example.cryptotrading.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record TradeResponse(Long tradeId, String symbol, String side, BigDecimal executedPrice, BigDecimal quantity, BigDecimal total, Instant executedAt) {}
