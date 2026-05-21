package com.example.userorder.domain.product;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.common.vo.Quantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private long unitPrice;

    private Product(String name, int stockQuantity, long unitPrice) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
    }

    public static Product create(String name, Quantity quantity, Money unitPrice) {
        return new Product(name, quantity.value(), unitPrice.value());
    }

    public void update(String name, Quantity stockQuantity, Money unitPrice) {
        this.name = name;
        this.stockQuantity = stockQuantity.value();
        this.unitPrice = unitPrice.value();
    }

    public void decreaseStock(Quantity orderQuantity){
        Quantity stockQuantity = Quantity.of(this.stockQuantity - orderQuantity.value());
        this.stockQuantity = stockQuantity.value();
    }
}