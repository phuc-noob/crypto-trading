package com.example.cryptotrading.dto;

import java.math.BigDecimal;

public record WalletBalance(
        String currency,
        BigDecimal balance
) {}
