package com.example.userorder.application.product.model;

import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.common.vo.Quantity;

public record ProductValues(
        String name,
        Quantity stockQuantity,
        Money unitPrice
) {
}