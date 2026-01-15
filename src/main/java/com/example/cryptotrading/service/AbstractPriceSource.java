package com.example.cryptotrading.service;

import com.example.cryptotrading.dto.SupportedSymbols;
import com.example.cryptotrading.dto.TickerPrice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractPriceSource<T extends TickerPrice> implements PriceSource<T> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    protected abstract String url();
    protected abstract Class<T[]> responseType();

    protected String httpGet() {
        return new RestTemplate().getForObject(url(), String.class);
    }

    @Override
    public Map<String, T> fetchPrices() {
        var body = httpGet();
        var prices = parseResponse(body);
        return Arrays.stream(prices)
                .filter(p -> SupportedSymbols.ALL.contains(p.symbol()))
                .collect(Collectors.toUnmodifiableMap(
                        TickerPrice::symbol,
                        Function.identity()
                ));
    }

    protected T[] parseResponse(String body) {
        try {
            return MAPPER.readValue(body, responseType());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to parse response", e);
        }
    }
}
