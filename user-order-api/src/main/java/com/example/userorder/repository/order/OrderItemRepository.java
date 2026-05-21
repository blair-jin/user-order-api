package com.example.userorder.repository.order;

import com.example.userorder.domain.order.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Slice<OrderItem> findByOrder_UserIdAndOrder_Id(Long userId, Long orderId, Pageable pageable);
}