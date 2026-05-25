package com.example.userorder.domain.order;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.common.vo.Quantity;
import com.example.userorder.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false, updatable = false)
    private long totalAmount = 0; // SNAPSHOT

    private Order(Long userId) {
        this.userId = userId;
        this.orderStatus = OrderStatus.ORDERED;
    }

    public static Order create(Long userId) {
        Objects.requireNonNull(userId);
        return new Order(userId);
    }

    public void addItem(Product product, Quantity orderQuantity, Money unitPrice) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(orderQuantity);
        Objects.requireNonNull(unitPrice);

        OrderItem item = OrderItem.create(this, product, orderQuantity, unitPrice);
        this.items.add(item);
        this.totalAmount += orderQuantity.value() * unitPrice.value();
    }
}