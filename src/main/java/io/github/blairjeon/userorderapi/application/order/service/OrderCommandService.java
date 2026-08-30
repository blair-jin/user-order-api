package io.github.blairjeon.userorderapi.application.order.service;

import io.github.blairjeon.userorderapi.application.cart.reader.CartReader;
import io.github.blairjeon.userorderapi.application.order.command.OrderCommandMapper;
import io.github.blairjeon.userorderapi.application.order.command.OrderItemCommand;
import io.github.blairjeon.userorderapi.application.order.reader.OrderReader;
import io.github.blairjeon.userorderapi.application.product.reader.ProductReader;
import io.github.blairjeon.userorderapi.domain.cart.CartItem;
import io.github.blairjeon.userorderapi.domain.order.Order;
import io.github.blairjeon.userorderapi.domain.order.OrderItem;
import io.github.blairjeon.userorderapi.domain.order.OrderStatus;
import io.github.blairjeon.userorderapi.domain.order.vo.OrderQuantity;
import io.github.blairjeon.userorderapi.domain.product.Product;
import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.EmptyCartException;
import io.github.blairjeon.userorderapi.exception.NOT_FOUND.ProductNotFoundException;
import io.github.blairjeon.userorderapi.repository.cart.CartItemRepository;
import io.github.blairjeon.userorderapi.repository.order.OrderItemRepository;
import io.github.blairjeon.userorderapi.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final CartReader cartReader;
    private final ProductReader productReader;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderReader orderReader;

    @Transactional
    public void createFromCart(Long userId){
        Long cartId = cartReader.getCartIdByUserId(userId);
        List<CartItem> items = getCartItems(cartId);

        Order order = orderRepository.save(Order.create(userId));

        Map<Long, Product> productMap = getProductMapWithLock(items, CartItem::getProductId);

        List<OrderItem> orderItems =
                items.stream().map(cartItem -> {
                    Product product = productMap.get(cartItem.getProductId());
                    product.decrease(cartItem.getOrderQuantity());

                    OrderItemCommand command = OrderCommandMapper.toCommand(product, cartItem);
                    return OrderItem.create(order.getId(), command);
                }).toList();

        updateTotalPrice(order, orderItems);
        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAllByCartId(cartId);
    }

    @Transactional
    public void createDirect(Long userId, Long productId, OrderQuantity orderQuantity){
        Order order = orderRepository.save(Order.create(userId));

        Product product = productReader.getProductByIdWithLock(productId);
        product.decrease(orderQuantity);

        OrderItemCommand command = OrderCommandMapper.toCommand(product, orderQuantity);
        OrderItem item = OrderItem.create(order.getId(), command);
        orderItemRepository.save(item);

        order.updateTotalPrice(item.getTotalPrice());
    }

    @Transactional
    public void cancel(Long userId, Long orderId){
        Order order = orderReader.getOrderByUserIdAndId(userId, orderId);

        List<OrderItem> items = orderReader.getItemsByOrderId(orderId);
        Map<Long, Product> productMap = getProductMapWithLock(items, OrderItem::getProductId);

        items.forEach(orderItem -> {
            Product product = productMap.get(orderItem.getProductId());
            product.increase(orderItem.getOrderQuantity());
        });

        order.updateTotalPrice(0);
        order.changeStatus(OrderStatus.CANCELED);
    }

    private List<CartItem> getCartItems(Long cartId){
        List<CartItem> items = cartReader.getItemsByCartId(cartId);

        if(items.isEmpty()){
            throw new EmptyCartException();
        }

        return items;
    }

    private <T> Map<Long, Product> getProductMapWithLock(
            List<T> items,
            Function<T, Long> productIdExtractor
    ){
        List<Long> productIds = items.stream()
                .map(productIdExtractor)
                .distinct()
                .sorted()
                .toList();

        List<Product> products = productReader.getAllByIdsWithPessimisticLock(productIds);

        if (products.size() != productIds.size()) {
            throw new ProductNotFoundException();
        }

        return products.stream()
                .collect(Collectors.toMap(Product::getId, product->product));
    }

    private void updateTotalPrice(Order order, List<OrderItem> items){
        long totalPrice = items.stream()
                .mapToLong(OrderItem::getTotalPrice)
                .sum();

        order.updateTotalPrice(totalPrice);
    }
}
