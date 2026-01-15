package com.example.cryptotrading.service;

import com.example.cryptotrading.dto.TickerPrice;

import java.util.Map;

public interface PriceSource<T extends TickerPrice> {
    Map<String, ? extends TickerPrice> fetchPrices();
    String source();
}
