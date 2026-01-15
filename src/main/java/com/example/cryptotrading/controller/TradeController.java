
package com.example.cryptotrading.controller;

import com.example.cryptotrading.dto.TradeRequest;
import com.example.cryptotrading.dto.TradeResponse;
import com.example.cryptotrading.repository.*;
import com.example.cryptotrading.service.TradeQueryService;
import com.example.cryptotrading.service.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
    private final TradeService tradeService;
    private final TradeQueryService tradeQueryService;

    public TradeController(TradeService tradeService, TradeQueryService tradeQueryService) {
        this.tradeService = tradeService;
        this.tradeQueryService = tradeQueryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TradeResponse createTrade(@RequestBody TradeRequest req) {
        return tradeService.executeTrade(req);
    }

    @GetMapping("/history")
    public List<TradeResponse> history(@RequestParam Long userId) {
        return tradeQueryService.historyForUser(userId);
    }
}
