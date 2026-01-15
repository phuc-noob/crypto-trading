package com.example.cryptotrading.dto;

import java.util.List;

public record WalletResponse(
        Long userId,
        List<WalletBalance> balances
) {}
