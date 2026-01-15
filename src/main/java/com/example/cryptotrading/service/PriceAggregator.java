package com.example.cryptotrading.service;

import com.example.cryptotrading.dto.TickerPrice;
import com.example.cryptotrading.entity.AggregatedPrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;

@Component
public class PriceAggregator {

    public AggregatedPrice aggregate(
            String symbol,
            Collection<? extends TickerPrice> prices) {

        var bestBid = prices.stream()
                .map(TickerPrice::bidPrice)
                .max(BigDecimal::compareTo)
                .orElseThrow();

        var bestAsk = prices.stream()
                .map(TickerPrice::askPrice)
                .min(BigDecimal::compareTo)
                .orElseThrow();

        return new AggregatedPrice(
                symbol,
                bestBid,
                bestAsk,
                Instant.now()
        );
    }
}
