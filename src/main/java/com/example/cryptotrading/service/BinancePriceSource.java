package com.example.cryptotrading.service;

import com.example.cryptotrading.dto.BinanceTickerPrice;
import org.springframework.stereotype.Component;

@Component
public final class BinancePriceSource extends AbstractPriceSource<BinanceTickerPrice> {

    @Override
    protected String url() {
        return "https://api.binance.com/api/v3/ticker/bookTicker";
    }

    @Override
    protected Class<BinanceTickerPrice[]> responseType() {
        return BinanceTickerPrice[].class;
    }

    @Override
    public String source() {
        return "BINANCE";
    }
}

