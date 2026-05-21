package com.example.userorder.domain.order;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.common.vo.Quantity;
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
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private long totalPrice = 0; // SNAPSHOT

    private Order(Long userId) {
        this.userId = Objects.requireNonNull(userId);
        this.orderStatus = OrderStatus.ORDERED;
    }

    public static Order create(Long userId) {
        return new Order(userId);
    }

    public void addItem(Long productId, Quantity orderQuantity, Money unitPrice) {
        OrderItem item = OrderItem.create(this, productId, orderQuantity, unitPrice);
        this.totalPrice += item.getUnitPrice() * item.getOrderQuantity();
        this.items.add(item);
    }
}