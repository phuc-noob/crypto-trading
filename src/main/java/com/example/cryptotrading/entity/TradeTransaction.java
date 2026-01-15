
package com.example.cryptotrading.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeTransaction {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    private String symbol;
    private String side;
    private double price;
    private double quantity;
    private double amount;
    private LocalDateTime createdAt;
}
