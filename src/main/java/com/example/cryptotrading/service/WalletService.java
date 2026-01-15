package com.example.cryptotrading.service;

import com.example.cryptotrading.dto.WalletBalance;
import com.example.cryptotrading.dto.WalletResponse;
import com.example.cryptotrading.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public WalletResponse getWallet(Long userId) {

        List<WalletBalance> balances = walletRepository.findByUserId(userId)
                .stream()
                .map(w -> new WalletBalance(w.getCurrency(), w.getBalance()))
                .toList();

        return new WalletResponse(userId, balances);
    }
}