package com.example.cryptotrading.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false, length = 16)
    private String symbol;

    @Column(nullable = false, precision = 38, scale = 16)
    private BigDecimal balance;

    public Wallet() {}

    public Wallet(User user, String usdt, int i) {
        this.user = user;
        this.symbol = usdt;
        this.balance = BigDecimal.valueOf(i);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return symbol;
    }
}