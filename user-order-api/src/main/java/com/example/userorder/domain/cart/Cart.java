package com.example.userorder.domain.cart;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "carts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private Long userId;

    private Cart(Long userId) {
        this.userId = Objects.requireNonNull(userId);
    }

    public static Cart create(Long userId) {
        return new Cart(userId);
    }
}