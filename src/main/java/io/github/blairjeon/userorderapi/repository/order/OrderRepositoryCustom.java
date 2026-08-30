package io.github.blairjeon.userorderapi.repository.order;

import io.github.blairjeon.userorderapi.domain.order.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface OrderRepositoryCustom {
    Slice<Order> findSlice(Specification<Order> spec, Pageable pageable);
}