package com.example.cryptotrading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HuobiTickerPrice extends TickerPrice{
    @JsonProperty("symbol")
    void setSymbol(String symbol) {
        this.symbol = symbol.toUpperCase();
    }

    @JsonProperty("bid")
    void setBid(BigDecimal bid) {
        this.bidPrice = bid;
    }

    @JsonProperty("ask")
    void setAsk(BigDecimal ask) {
        this.askPrice = ask;
    }
}
