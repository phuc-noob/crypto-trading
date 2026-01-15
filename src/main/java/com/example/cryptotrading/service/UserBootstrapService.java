
package com.example.cryptotrading.service;

import com.example.cryptotrading.entity.*;
import com.example.cryptotrading.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class UserBootstrapService {
    private final UserRepository userRepo;
    private final WalletRepository walletRepo;

    public UserBootstrapService(UserRepository userRepo, WalletRepository walletRepo) {
        this.userRepo = userRepo;
        this.walletRepo = walletRepo;
    }

    @PostConstruct
    public void init() {
        User user = new User(1L, "demo-user");
        userRepo.save(user);
        walletRepo.save(new Wallet(user, "USDT", 500000));
    }
}
