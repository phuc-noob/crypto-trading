package com.example.cryptotrading.service;

import com.example.cryptotrading.dto.TradeResponse;
import com.example.cryptotrading.entity.Trade;
import com.example.cryptotrading.entity.User;
import com.example.cryptotrading.repository.TradeRepository;
import com.example.cryptotrading.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeQueryService {

    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;

    public TradeQueryService(TradeRepository tradeRepository, UserRepository userRepository) {
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
    }

    public List<TradeResponse> historyForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Trade> trades = tradeRepository.findByUser(user);
        return trades.stream()
                .sorted(Comparator.comparing(Trade::getExecutedAt).reversed())
                .map(t -> new TradeResponse(
                        t.getId(),
                        t.getSymbol(),
                        t.getSide(),
                        t.getExecutedPrice(),
                        t.getQuantity(),
                        t.getTotal(),
                        t.getExecutedAt()
                ))
                .collect(Collectors.toList());
    }
}
