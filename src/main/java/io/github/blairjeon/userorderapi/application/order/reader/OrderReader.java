package io.github.blairjeon.userorderapi.application.order.reader;

import io.github.blairjeon.userorderapi.domain.order.Order;
import io.github.blairjeon.userorderapi.domain.order.OrderItem;
import io.github.blairjeon.userorderapi.exception.NOT_FOUND.OrderNotFoundException;
import io.github.blairjeon.userorderapi.repository.order.OrderItemRepository;
import io.github.blairjeon.userorderapi.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderReader {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public Order getOrderByUserIdAndId(Long userId, Long orderId){
        return orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    public List<OrderItem> getItemsByOrderId(Long orderId){
        return orderItemRepository.findAllByOrderId(orderId);
    }
}