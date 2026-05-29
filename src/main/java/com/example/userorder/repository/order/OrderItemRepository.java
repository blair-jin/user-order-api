package com.example.userorder.repository.order;

import com.example.userorder.domain.order.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrder_IdAndIdAndOrder_UserId(Long orderId, Long orderItemId, Long userId);

    Slice<OrderItem> findByOrder_Id(Long orderId, Pageable pageable);

    List<OrderItem> findAllByOrder_Id(Long orderId);
}