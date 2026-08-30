package io.github.blairjeon.userorderapi.repository.order;

import io.github.blairjeon.userorderapi.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends
        JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order>,
        OrderRepositoryCustom
{
    Optional<Order> findByUserIdAndId(Long userId, Long id);
}