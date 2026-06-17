package io.github.blairjin.user_order_api.application.order.service;

import io.github.blairjin.user_order_api.application.order.reader.OrderReader;
import io.github.blairjin.user_order_api.domain.order.Order;
import io.github.blairjin.user_order_api.domain.order.OrderItem;
import io.github.blairjin.user_order_api.dto.order.OrderItemResponse;
import io.github.blairjin.user_order_api.dto.order.OrderResponse;
import io.github.blairjin.user_order_api.dto.order.SearchOrderCondition;
import io.github.blairjin.user_order_api.repository.order.OrderRepository;
import io.github.blairjin.user_order_api.repository.order.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {
    private final OrderRepository orderRepository;
    private final OrderReader orderReader;

    public Slice<OrderResponse> search(
            Long userId,
            SearchOrderCondition condition,
            Pageable pageable
    ) {
        Specification<Order> spec = Specification.allOf(
                OrderSpecification.userIdEq(userId),
                OrderSpecification.statusEq(condition.orderStatus()),
                OrderSpecification.totalPriceGoe(condition.minPrice()),
                OrderSpecification.totalPriceLoe(condition.maxPrice()),
                OrderSpecification.createdAtGoe(condition.startDate()),
                OrderSpecification.createdAtLoe(condition.endDate())
        );

        Slice<Order> orders = orderRepository.findSlice(spec, pageable);

        return orders.map(OrderResponse::from);
    }

    public List<OrderItemResponse> get(Long userId, Long orderId){
        Order order = orderReader.getOrderByUserIdAndId(userId, orderId);
        List<OrderItem> items = orderReader.getItemsByOrderId(order.getId());

        return items.stream().map(OrderItemResponse::from).toList();
    }
}