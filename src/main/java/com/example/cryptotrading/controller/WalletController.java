package com.example.cryptotrading.controller;

import com.example.cryptotrading.dto.WalletResponse;
import com.example.cryptotrading.service.WalletService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public WalletResponse wallet(@RequestParam Long userId) {
        return walletService.getWallet(userId);
    }

}