package com.example.cryptotrading.repository;

import com.example.cryptotrading.entity.User;
import com.example.cryptotrading.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findByUserId(Long userId);
    Optional<Wallet> findByUserAndSymbol(User user, String symbol);
}