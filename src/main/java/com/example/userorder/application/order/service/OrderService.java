package com.example.userorder.application.order.service;

import com.example.userorder.application.cart.reader.CartReader;
import com.example.userorder.application.order.command.CreateOrderCommand;
import com.example.userorder.application.order.reader.OrderReader;
import com.example.userorder.application.product.reader.ProductReader;
import com.example.userorder.common.exception.ProductNotFoundException;
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
import com.example.userorder.repository.product.ProductRepository;
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
    private final ProductRepository productRepository;

    @Transactional
    public Order create(Long userId, List<CreateOrderCommand> command) {
        List<Long> productIds = extractProductIds(command);
        Map<Long, Quantity> quantityMap = createQuantityMap(command);
        Order order = Order.create(userId);

        List<Product> products = getValidatedProductsWithLock(productIds);
        addOrderItems(order, products, quantityMap);
        orderRepository.save(order);

        return order;
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

    @Transactional
    public void cancel(Long userId, Long orderId) {
        Order order = orderReader.getOrderByUserIdAndId(userId, orderId);
        order.cancel();

        List<OrderItem> items = orderReader.getAllByOrderId(orderId);
        List<Long> productIds = items.stream()
                .map(OrderItem::getProductId)
                .distinct()
                .sorted()
                .toList();

        List<Product> productList = getValidatedProductsWithLock(productIds);
        Map<Long, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, p -> p));

        for (OrderItem i : items) {
            Product product = productMap.get(i.getProductId());
            Quantity quantity = Quantity.of(i.getOrderQuantity());
            product.increaseStock(quantity);
        }
    }

    private List<Long> extractProductIds(List<CreateOrderCommand> command) {
        return command.stream()
                .map(CreateOrderCommand::productId)
                .distinct()
                .sorted()
                .toList();
    }

    private Map<Long, Quantity> createQuantityMap(List<CreateOrderCommand> command) {
        return command.stream()
                .collect(Collectors.toMap(
                        CreateOrderCommand::productId,
                        CreateOrderCommand::orderQuantity
                ));
    }

    private List<Product> getValidatedProductsWithLock(List<Long> productIds) {
        List<Product> products = productReader.getProductsByIdsWithPessimisticLock(productIds);

        if (products.size() != productIds.size()) {
            throw new ProductNotFoundException();
        }

        return products;
    }

    private void addOrderItems(Order order, List<Product> products, Map<Long, Quantity> quantityMap) {
        for (Product p : products) {
            Quantity orderQuantity = quantityMap.get(p.getId());
            Money unitPrice = Money.of(p.getUnitPrice());
            p.decreaseStock(orderQuantity);
            order.addItem(p, orderQuantity, unitPrice);
        }
    }
}