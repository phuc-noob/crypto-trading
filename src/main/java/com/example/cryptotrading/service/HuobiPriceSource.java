package com.example.cryptotrading.service;

import com.example.cryptotrading.dto.HuobiTickerPrice;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public final class HuobiPriceSource extends AbstractPriceSource<HuobiTickerPrice> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected String url() {
        return "https://api.huobi.pro/market/tickers";
    }

    @Override
    protected Class<HuobiTickerPrice[]> responseType() {
        return HuobiTickerPrice[].class;
    }

    @Override
    public String source() {
        return "HUOBI";
    }

    @Override
    protected HuobiTickerPrice[] parseResponse(String body) {
        try {
            HuobiResponse response = MAPPER.readValue(body, HuobiResponse.class);
            return response.data().toArray(HuobiTickerPrice[]::new);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to parse Huobi response", e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record HuobiResponse(@JsonProperty("data") List<HuobiTickerPrice> data) {}
}