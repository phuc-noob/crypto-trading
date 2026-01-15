package com.example.cryptotrading.scheduler;

import com.example.cryptotrading.dto.TickerPrice;
import com.example.cryptotrading.repository.AggregatedPriceRepository;
import com.example.cryptotrading.service.PriceAggregator;
import com.example.cryptotrading.service.PriceSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@EnableScheduling
public class PriceAggregationScheduler {

    private final List<PriceSource<? extends TickerPrice>> providers;
    private final AggregatedPriceRepository repository;
    private final PriceAggregator aggregator;

    @Autowired
    public PriceAggregationScheduler(
            List<PriceSource<? extends TickerPrice>> providers,
            AggregatedPriceRepository repository,
            PriceAggregator aggregator) {
        this.providers = providers;
        this.repository = repository;
        this.aggregator = aggregator;
    }

    @Scheduled(fixedRate = 10_000)
    public void aggregatePrices() {

        Map<String, List<TickerPrice>> pricesBySymbol = new HashMap<>();

        for (var provider : providers) {
            Map<String, TickerPrice> fetched;
            try {
                fetched = (Map<String, TickerPrice>) provider.fetchPrices();
            } catch (Exception ex) {
                continue;
            }
            if (fetched == null) continue;

            fetched.forEach((symbol, price) -> {
                String normalized = normalizeToSupported(symbol);
                if (normalized == null) return;
                pricesBySymbol
                        .computeIfAbsent(normalized, k -> new ArrayList<>())
                        .add(price);
            });
        }

        pricesBySymbol.forEach((symbol, prices) -> {
            var aggregated = aggregator.aggregate(symbol, prices);
            repository.save(aggregated);
        });

    }

    private static String normalizeToSupported(String raw) {
        if (raw == null) return null;
        String cleaned = raw.toUpperCase().replaceAll("[^A-Z0-9]", "");
        if (cleaned.startsWith("ETH") && cleaned.endsWith("USDT")) return "ETHUSDT";
        if (cleaned.startsWith("BTC") && cleaned.endsWith("USDT")) return "BTCUSDT";
        return null;
    }
}