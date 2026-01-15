package com.example.cryptotrading.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false, length = 16)
    private String symbol;

    @Column(nullable = false, length = 8)
    private String side; // "BUY" or "SELL"

    @Column(nullable = false, precision = 32, scale = 16)
    private BigDecimal executedPrice;

    @Column(nullable = false, precision = 32, scale = 16)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 38, scale = 16)
    private BigDecimal total;

    @Column(nullable = false)
    private Instant executedAt;

    @PrePersist
    void onCreate() {
        this.executedAt = Instant.now();
    }

    // getters / setters

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getSide() { return side; }
    public void setSide(String side) { this.side = side; }
    public BigDecimal getExecutedPrice() { return executedPrice; }
    public void setExecutedPrice(BigDecimal executedPrice) { this.executedPrice = executedPrice; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public Instant getExecutedAt() { return executedAt; }
    public void setExecutedAt(Instant executedAt) { this.executedAt = executedAt; }
}
