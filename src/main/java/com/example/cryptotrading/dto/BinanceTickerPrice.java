package com.example.cryptotrading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceTickerPrice extends TickerPrice {
    @JsonProperty("symbol")
    void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("bidPrice")
    void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    @JsonProperty("askPrice")
    void setAskPrice(BigDecimal askPrice) {
        this.askPrice = askPrice;
    }
}
