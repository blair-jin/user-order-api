package io.github.blairjin.user_order_api.repository.order;

import io.github.blairjin.user_order_api.domain.order.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface OrderRepositoryCustom {
    Slice<Order> findSlice(Specification<Order> spec, Pageable pageable);
}