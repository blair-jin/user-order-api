package com.example.userorder.repository.order;

import com.example.userorder.domain.order.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Slice<Order> findByUserId(Long userId, Pageable pageable);

}