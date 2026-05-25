package com.example.userorder.application.order.service;

import com.example.userorder.application.cart.reader.CartReader;
import com.example.userorder.application.order.reader.OrderReader;
import com.example.userorder.application.product.reader.ProductReader;
import com.example.userorder.common.exception.ProductNotFoundException;
import com.example.userorder.domain.cart.Cart;
import com.example.userorder.domain.cart.CartItem;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.common.vo.Quantity;
import com.example.userorder.domain.order.Order;
import com.example.userorder.domain.order.OrderItem;
import com.example.userorder.domain.product.Product;
import com.example.userorder.dto.order.OrderItemResponse;
import com.example.userorder.dto.order.OrderResponse;
import com.example.userorder.dto.order.OrderSearchCondition;
import com.example.userorder.repository.order.OrderItemRepository;
import com.example.userorder.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final CartReader cartReader;
    private final ProductReader productReader;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderReader orderReader;

    @Transactional
    public void create(Long userId) {
        Cart cart = cartReader.getByUserId(userId);
        List<CartItem> items = cartReader.getAllByCartId(cart.getId());

        if (items.isEmpty()) {
            throw new IllegalArgumentException("CART_EMPTY");
        }

        List<Long> cartItemIds = items.stream().map(CartItem::getProductId).toList();
        List<Product> products = productReader.getProductsByIds(cartItemIds);

        Map<Long, Product> productMap =
                products.stream().collect(Collectors.toMap(Product::getId, product -> product));

        Order order = Order.create(userId);
        for (CartItem item : items) {
            Product product = productMap.get(item.getProductId());

            if (product == null) {
                throw new ProductNotFoundException();
            }

            Money unitPrice = Money.of(product.getUnitPrice());
            Quantity orderQuantity = Quantity.of(item.getOrderQuantity());

            product.decreaseStock(orderQuantity);
            order.addItem(product, orderQuantity, unitPrice);
        }

        orderRepository.save(order);
    }

    public Slice<OrderResponse> searchOrders(Long userId, OrderSearchCondition condition, Pageable pageable) {
        return orderRepository.searchOrders(userId, condition, pageable)
                .map(OrderResponse::from);
    }

    public Slice<OrderItemResponse> searchOrderItems(Long userId, Long orderId, Pageable pageable) {
        orderReader.validateOrderWithUserId(userId, orderId);

        return orderItemRepository.findByOrder_Id(orderId, pageable)
                .map(OrderItemResponse::from);
    }

    public OrderItemResponse getOrderItem(Long userId, Long orderId, Long orderItemId) {
        OrderItem item = orderReader.getItemByOrderIdAndIdAndUserId(orderId, orderItemId, userId);
        return OrderItemResponse.from(item);
    }
}