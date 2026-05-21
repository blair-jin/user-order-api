package com.example.userorder.domain.order;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.common.vo.Quantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false, updatable = false)
    private Long productId;

    @Column(nullable = false, updatable = false)
    private int orderQuantity;

    @Column(nullable = false, updatable = false)
    private long unitPrice; // SNAPSHOT

    private OrderItem(Order order, Long productId, int orderQuantity, long unitPrice) {
        this.order = Objects.requireNonNull(order);
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.unitPrice = unitPrice;
    }

    static OrderItem create(Order order, Long productId, Quantity orderQuantity, Money unitPrice) {
        return new OrderItem(order, productId, orderQuantity.value(), unitPrice.value());
    }
}