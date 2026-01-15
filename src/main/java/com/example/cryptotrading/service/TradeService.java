package com.example.cryptotrading.service;

import com.example.cryptotrading.dto.TradeRequest;
import com.example.cryptotrading.dto.TradeResponse;
import com.example.cryptotrading.entity.AggregatedPrice;
import com.example.cryptotrading.entity.Trade;
import com.example.cryptotrading.entity.User;
import com.example.cryptotrading.entity.Wallet;
import com.example.cryptotrading.repository.AggregatedPriceRepository;
import com.example.cryptotrading.repository.TradeRepository;
import com.example.cryptotrading.repository.UserRepository;
import com.example.cryptotrading.repository.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;

@Service
public class TradeService {

    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final UserRepository userRepository;
    private final TradeRepository tradeRepository;
    private final WalletRepository walletRepository;

    public TradeService(AggregatedPriceRepository aggregatedPriceRepository,
                        UserRepository userRepository,
                        TradeRepository tradeRepository,
                        WalletRepository userWalletRepository) {
        this.aggregatedPriceRepository = aggregatedPriceRepository;
        this.userRepository = userRepository;
        this.tradeRepository = tradeRepository;
        this.walletRepository = userWalletRepository;
    }

    @Transactional
    public TradeResponse executeTrade(TradeRequest req) {
        var symbol = req.symbol() == null ? null : req.symbol().toUpperCase().replaceAll("[^A-Z0-9]", "");
        if (symbol == null || !TradingPairFactory.isSupported(symbol)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported symbol");
        }

        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        AggregatedPrice latest = aggregatedPriceRepository.findAll().stream()
                .filter(p -> symbol.equalsIgnoreCase(p.getSymbol()))
                .max(Comparator.comparing(AggregatedPrice::getUpdatedAt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No aggregated price available"));

        BigDecimal executedPrice;
        var side = req.side() == null ? "" : req.side().toUpperCase();
        if ("BUY".equals(side)) {
            executedPrice = latest.getAskPrice();
        } else if ("SELL".equals(side)) {
            executedPrice = latest.getBidPrice();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid side (use BUY or SELL)");
        }

        if (executedPrice == null || req.quantity() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing price or quantity");
        }

        BigDecimal total = executedPrice.multiply(req.quantity());

        if (symbol.length() <= 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid trading pair");
        }
        String quote = symbol.substring(symbol.length() - 4);
        String base = symbol.substring(0, symbol.length() - 4);

        Wallet baseWallet = getOrCreateWallet(user, base);
        Wallet quoteWallet = getOrCreateWallet(user, quote);

        // apply balance changes with checks
        if ("BUY".equals(side)) {
            if (quoteWallet.getBalance() == null || quoteWallet.getBalance().compareTo(total) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds in " + quote);
            }
            quoteWallet.setBalance(quoteWallet.getBalance().subtract(total));
            baseWallet.setBalance((baseWallet.getBalance() == null ? BigDecimal.ZERO : baseWallet.getBalance()).add(req.quantity()));
        } else {
            if (baseWallet.getBalance() == null || baseWallet.getBalance().compareTo(req.quantity()) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds in " + base);
            }
            baseWallet.setBalance(baseWallet.getBalance().subtract(req.quantity()));
            quoteWallet.setBalance((quoteWallet.getBalance() == null ? BigDecimal.ZERO : quoteWallet.getBalance()).add(total));
        }

        walletRepository.save(baseWallet);
        walletRepository.save(quoteWallet);

        Trade trade = new Trade();
        trade.setUser(user);
        trade.setSymbol(symbol);
        trade.setSide(side);
        trade.setExecutedPrice(executedPrice);
        trade.setQuantity(req.quantity());
        trade.setTotal(total);

        trade = tradeRepository.save(trade);

        return new TradeResponse(trade.getId(), trade.getSymbol(), trade.getSide(), trade.getExecutedPrice(), trade.getQuantity(), trade.getTotal(), trade.getExecutedAt());
    }

    private Wallet getOrCreateWallet(User user, String symbol) {
        return walletRepository.findByUserAndSymbol(user, symbol)
                .orElseGet(() -> {
                    Wallet w = new Wallet();
                    w.setUser(user);
                    w.setSymbol(symbol);
                    w.setBalance(BigDecimal.ZERO);
                    return walletRepository.save(w);
                });
    }
}
