package com.example.userorder.domain.order;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.common.vo.Quantity;
import com.example.userorder.domain.product.Product;
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
    private String productName;

    @Column(nullable = false, updatable = false)
    private int orderQuantity;

    @Column(nullable = false, updatable = false)
    private long unitPrice; // SNAPSHOT

    private OrderItem(Order order, Product product, int orderQuantity, long unitPrice) {
        this.order = order;
        this.productId = product.getId();
        this.productName = product.getName();
        this.orderQuantity = orderQuantity;
        this.unitPrice = unitPrice;
    }

    static OrderItem create(Order order, Product product, Quantity orderQuantity, Money unitPrice) {
        Objects.requireNonNull(order);
        Objects.requireNonNull(product);
        Objects.requireNonNull(orderQuantity);
        Objects.requireNonNull(unitPrice);

        return new OrderItem(order, product, orderQuantity.value(), unitPrice.value());
    }
}