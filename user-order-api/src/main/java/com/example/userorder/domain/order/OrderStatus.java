package com.example.userorder.domain.order;

import java.util.Set;

public enum OrderStatus {
    PENDING,
    ORDERED,
    DELIVERING,
    DELIVERED,
    COMPLETE,
    CANCELED;

    private Set<OrderStatus> nextStatuses;

    static {
        PENDING.nextStatuses = Set.of(ORDERED, CANCELED);
        ORDERED.nextStatuses = Set.of(DELIVERING, CANCELED);
        DELIVERING.nextStatuses = Set.of(DELIVERED, CANCELED);
        DELIVERED.nextStatuses = Set.of(CANCELED);
        COMPLETE.nextStatuses = Set.of();
        CANCELED.nextStatuses = Set.of();
    }

    public boolean canChangeTo(OrderStatus nextStatus) {
        return nextStatuses.contains(nextStatus);
    }
}