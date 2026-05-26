package com.example.userorder.repository.order;

import com.example.userorder.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    boolean existsByUserIdAndId(Long userId, Long orderId);

    Optional<Order> findByUserIdAndId(Long userId, Long orderId);


}