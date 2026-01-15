
package com.example.cryptotrading.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@RequiredArgsConstructor
@DynamicUpdate
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"symbol"}))
public class AggregatedPrice {
    @Id
    private String symbol;
    private double bestBid;
    private double bestAsk;
    private Instant updatedAt;

    public AggregatedPrice(String symbol, BigDecimal bestBid, BigDecimal bestAsk, Instant now) {
        this.symbol = symbol;
        this.bestBid = bestBid.doubleValue();
        this.bestAsk = bestAsk.doubleValue();
        this.updatedAt = now;
    }

    public String getSymbol() {
        return symbol;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public BigDecimal getAskPrice() {
        return BigDecimal.valueOf(bestAsk);
    }

    public BigDecimal getBidPrice() {
        return BigDecimal.valueOf(bestBid);
    }
}
