
package com.example.cryptotrading.repository;

import com.example.cryptotrading.entity.TradeTransaction;
import com.example.cryptotrading.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {
    List<TradeTransaction> findByUser(User user);
}
