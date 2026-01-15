package com.example.cryptotrading.dto;

import java.math.BigDecimal;

public abstract class TickerPrice {

    protected String symbol;
    protected BigDecimal bidPrice;
    protected BigDecimal askPrice;

    public String symbol() {
        return symbol;
    }

    public BigDecimal bidPrice() {
        return bidPrice;
    }

    public BigDecimal askPrice() {
        return askPrice;
    }
}

