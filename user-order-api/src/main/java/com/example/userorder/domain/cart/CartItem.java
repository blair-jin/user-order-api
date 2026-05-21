package com.example.userorder.domain.cart;

import com.example.userorder.domain.common.vo.Quantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
        name = "cart_items",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cart_id", "product_id"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false, updatable = false)
    private Cart cart;

    @Column(nullable = false, updatable = false)
    private Long productId;

    @Column(nullable = false)
    private int orderQuantity;

    private CartItem(Cart cart, Long productId, int orderQuantity) {
        this.cart = cart;
        this.productId = productId;
        this.orderQuantity = orderQuantity;
    }

    public static CartItem create(Cart cart, Long productId, Quantity orderQuantity) {
        return new CartItem(cart, productId, orderQuantity.value());
    }

    public void increaseQuantity(Quantity orderQuantity) {
        this.orderQuantity += orderQuantity.value();
    }

    public void updateQuantity(Quantity orderQuantity) {
        this.orderQuantity = orderQuantity.value();
    }
}