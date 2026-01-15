
package com.example.cryptotrading.controller;

import com.example.cryptotrading.repository.AggregatedPriceRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final AggregatedPriceRepository repo;

    public PriceController(AggregatedPriceRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Object prices() {
        return repo.findAll();
    }
}
