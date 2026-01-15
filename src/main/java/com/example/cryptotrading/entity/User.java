
package com.example.cryptotrading.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String username;

    public User(long l, String s) {
        this.username = s;
        this.id = l;
    }
}
